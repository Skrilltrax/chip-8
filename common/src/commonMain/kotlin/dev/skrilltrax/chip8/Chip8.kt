@file:Suppress("FunctionName")

package dev.skrilltrax.chip8

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import dev.skrilltrax.chip8.cpu.CPU
import dev.skrilltrax.chip8.cpu.Memory
import dev.skrilltrax.chip8.cpu.Registers
import dev.skrilltrax.chip8.cpu.Stack
import dev.skrilltrax.chip8.cpu.Timers
import dev.skrilltrax.chip8.display.Display
import dev.skrilltrax.chip8.display.DisplayMatrix
import dev.skrilltrax.chip8.keyboard.Keyboard

class Chip8 {

  private val display = Display()
  private val keyboard = Keyboard()
  private val memory = Memory()
  private val registers = Registers()
  private val stack = Stack()
  private val timers = Timers()
  private val cpu = CPU(display, keyboard, memory, registers, stack, timers)

  fun loadRom(rom: ByteArray) {
    val intRom = rom.map { it.toInt() }
    memory.loadIntoMemory(intRom.toIntArray())
  }

  @Composable
  fun Render(scale: Int) {
    val displayMatrix = display.displayMatrixFlow.collectAsState(DisplayMatrix())

    LaunchedEffect(Unit) {
      while (true) {
        cpu.cycle()
      }
    }

    Screen(displayMatrix.value, Modifier.fillMaxSize(), scale)
  }

  @Composable
  private fun Screen(displayMatrix: DisplayMatrix, modifier: Modifier = Modifier, scale: Int = Display.SCALE) {
    Canvas(modifier) {
      for (y in 0 until Display.ROWS) {
        for (x in 0 until Display.COLUMNS) {
          val color = if (displayMatrix.getPixel(x, y)) Color.White else Color.Black
          drawRect(
            color = color,
            topLeft = Offset((x * scale).toFloat(), (y * scale).toFloat()),
            size = Size(scale.toFloat(), scale.toFloat())
          )
        }
      }
    }
  }
}
