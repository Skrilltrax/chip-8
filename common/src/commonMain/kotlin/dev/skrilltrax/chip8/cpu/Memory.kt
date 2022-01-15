package dev.skrilltrax.chip8.cpu

import dev.skrilltrax.chip8.cpu.CPU.Companion.PROGRAM_START_ADDRESS

class Memory {

  private val memory = IntArray(4096)

  init { loadSpritesIntoMemory() }

  fun read(address: Int): Int {
    return memory[address]
  }

  fun loadIntoMemory(program: IntArray) {
    program.forEachIndexed { index, byte -> memory[PROGRAM_START_ADDRESS + index] = byte }
  }

  private fun loadSpritesIntoMemory() {
  }
}
