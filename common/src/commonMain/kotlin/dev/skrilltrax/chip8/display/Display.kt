@file:Suppress("FunctionName")
package dev.skrilltrax.chip8.display

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color

class Display {

  private val displayMatrix = DisplayMatrix.createEmpty()

  fun clear() {
    displayMatrix.clear()
  }

  fun setPixel(x: Int, y: Int): Boolean {
    return displayMatrix.setPixel(x, y)
  }

  @Composable
  fun Render() {
    val displayMatrix = remember { DisplayMatrix.createEmpty() }

    Screen(displayMatrix, Modifier.fillMaxSize())
  }

  @Composable
  fun Screen(displayMatrix: DisplayMatrix, modifier: Modifier = Modifier, scale: Int = SCALE) {
    Canvas(modifier) {
      for (y in 0 until ROWS) {
        for (x in 0 until COLUMNS) {
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

  companion object {
    const val COLUMNS = 64
    const val ROWS = 32
    const val SCALE = 100
  }
}