package dev.skrilltrax.chip8.cpu

import dev.skrilltrax.chip8.display.Display
import dev.skrilltrax.chip8.keyboard.Keyboard
import kotlin.random.Random

class CPU(
  private val display: Display,
  private val keyboard: Keyboard,
  private val memory: Memory,
  private val registers: Registers,
  private val stack: Stack,
  private val timers: Timers
) {

  // Some instructions require pausing, such as Fx0A.
  private var paused = false

  private val speed = 100

  fun cycle() {
    repeat(speed) {
      if (!paused) {
        val opcode = (memory.read(registers.pc) shl 8) or memory.read(registers.pc + 1)
        executeInstruction(opcode)
      }
    }

    if (!paused) {
      timers.tick();
    }

    // TODO: Play sound
    // TODO: Render on screen
  }

  /*
   * In these listings, the following variables are used:
   *
   * nnn or addr - A 12-bit value, the lowest 12 bits of the instruction
   * n or nibble - A 4-bit value, the lowest 4 bits of the instruction
   * x - A 4-bit value, the lower 4 bits of the high byte of the instruction
   * y - A 4-bit value, the upper 4 bits of the low byte of the instruction
   * kk or byte - An 8-bit value, the lowest 8 bits of the instruction
   */
  @Suppress("NAME_SHADOWING")
  private fun executeInstruction(opcode: Int) {
    registers.pc += 2

    val nnn = opcode and 0x0FFF
    val n = opcode and 0x000F
    val x = (opcode and 0x0F00) shr 8
    val y = (opcode and 0x00F0) shr 4
    val kk = opcode and 0x00FF

    when (Opcode.getOpcode(opcode)) {
      Opcode.SYS -> {}
      Opcode.CLS -> display.clear()
      Opcode.RET -> registers.pc = stack.pop()
      Opcode.JP -> registers.pc = nnn
      Opcode.CALL -> {
        stack.push(registers.pc)
        registers.pc = nnn
      }
      Opcode.SE_VX -> if (registers.v[x] == kk) registers.pc += 2
      Opcode.SNE_VX -> if (registers.v[x] != kk) registers.pc += 2
      Opcode.SE_VX_VY -> if (registers.v[x] == registers.v[y]) registers.pc += 2
      Opcode.LD_VX -> registers.v[x] = kk
      Opcode.ADD_VX -> registers.v[x] += kk
      Opcode.LD_VX_VY -> registers.v[x] = registers.v[y]
      Opcode.OR_VX_VY -> registers.v[x] = registers.v[x] or registers.v[y]
      Opcode.AND_VX_VY -> registers.v[x] = registers.v[x] and registers.v[y]
      Opcode.XOR_VX_VY -> registers.v[x] = registers.v[x] xor registers.v[y]
      Opcode.ADD_VX_VY -> {
        val sum = registers.v[x] + registers.v[y]
        registers.v[0xF] = if (sum > 255) 1 else 0
        registers.v[x] = (sum and 0xFF)
      }
      Opcode.SUB_VX_VY -> {
        val diff = registers.v[x] - registers.v[y]
        if (diff > 0) registers.v[0xF] = 1 else registers.v[0xF] = 0
        // overflow protection
        if (diff < 0) registers.v[x] = 0xFF + diff + 1 else registers.v[x] = diff
      }
      Opcode.SHR_VX_VY -> {
        registers.v[0xF] = registers.v[x] and 1
        registers.v[x] = registers.v[x] shr 1
      }
      Opcode.SUBN_VX_VY -> {
        val diff = registers.v[y] - registers.v[x]
        if (diff > 0) registers.v[0xF] = 1 else registers.v[0xF] = 0
        // overflow protection
        if (diff < 0) registers.v[x] = 0xFF + diff + 1 else registers.v[x] = diff
      }
      Opcode.SHL_VX_VY -> {
        registers.v[0xF] = registers.v[x] and 0x80
        registers.v[x] = registers.v[x] shl 1
      }
      Opcode.SNE_VX_VY -> if (registers.v[x] != registers.v[y]) registers.pc += 2
      Opcode.LD_I -> registers.i = nnn
      Opcode.JP_V0 -> registers.pc = nnn + registers.v[0]
      Opcode.RND_VX -> registers.v[x] = Random.nextInt(256) and kk
      Opcode.DRW_VX_VY -> {
        val height = n
        val width = 8

        registers.v[0xF] = 0

        for (row in 0 until height) {
          var sprite = memory.read(registers.i + row)

          for (column in 0 until width) {
            if ((sprite and 0x80) > 0) {
              if (display.setPixel(registers.v[x] + column, registers.v[y] + row)) {
                registers.v[0xF] = 1
              }
            }

            sprite = sprite shl 1
          }
        }
      }
      Opcode.SKP_VX -> if (keyboard.isKeyPressed(registers.v[x])) registers.pc += 2
      Opcode.SKNP_VX -> if (!keyboard.isKeyPressed(registers.v[x])) registers.pc += 2
      Opcode.LD_VX_DT -> registers.v[x] = timers.delayTimer
      Opcode.LD_VX_K -> {
        paused = true
        val key = keyboard.waitForKeyPress()
        if (key != -1) {
          registers.v[x] = key
          paused = false
        }
      }
      Opcode.LD_DT_VX -> timers.delayTimer = registers.v[x]
      Opcode.LD_ST_VX -> timers.soundTimer = registers.v[x]
      Opcode.ADD_I_VX -> registers.i += registers.v[x]
      Opcode.LD_F_VX -> registers.i = registers.v[x] * 5
      Opcode.LD_B_VX -> {
        val value = registers.v[x]
        memory.write(registers.i, value / 100)
        memory.write(registers.i + 1, (value % 100) / 10)
        memory.write(registers.i + 2, value % 10)
      }
      Opcode.LD_I_VX -> {
        for (registerIndex in 0..x) {
          memory.write(registers.i + registerIndex, registers.v[registerIndex])
        }
      }
      Opcode.LD_VX_I -> {
        for (registerIndex in 0..x) {
          registers.v[registerIndex] = memory.read(registers.i + registerIndex)
        }
      }
    }
  }

  companion object {
    const val PROGRAM_START_ADDRESS = 512 // 0x200
  }
}