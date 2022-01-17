@file:Suppress("FunctionName")

package dev.skrilltrax.chip8

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import dev.skrilltrax.chip8.cpu.*
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

  @Composable
  fun Render() {
    val displayMatrix = remember { display.displayMatrix }

    LaunchedEffect(Unit) {
      while (true) {
        cpu.cycle()
      }
    }

    Screen(displayMatrix, Modifier.fillMaxSize())
  }

  @Composable
  fun Screen(displayMatrix: DisplayMatrix, modifier: Modifier = Modifier, scale: Int = Display.SCALE) {
    Canvas(modifier) {
      for (y in 0 until Display.ROWS) {
        for (x in 0 until Display.COLUMNS) {
          val color = if (displayMatrix.getPixel(x, y)) Color.Black else Color.White
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
