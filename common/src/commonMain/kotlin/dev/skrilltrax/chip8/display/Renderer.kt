package dev.skrilltrax.chip8.display

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import dev.skrilltrax.chip8.display.DisplayMatrix.Companion.COLUMNS
import dev.skrilltrax.chip8.display.DisplayMatrix.Companion.ROWS

private const val SCALE = 100

@Composable
fun Render(modifier: Modifier, displayMatrix: DisplayMatrix, scale: Int = SCALE) {
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
