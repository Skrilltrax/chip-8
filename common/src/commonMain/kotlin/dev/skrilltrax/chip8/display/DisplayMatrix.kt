package dev.skrilltrax.chip8.display

import dev.skrilltrax.chip8.display.Display.Companion.COLUMNS
import dev.skrilltrax.chip8.display.Display.Companion.ROWS
import kotlin.jvm.JvmInline

@JvmInline value class DisplayMatrix(private val matrix: IntArray = IntArray(ROWS * COLUMNS)) {

  fun setPixel(x: Int, y: Int): Boolean {
    val pixelLocation = findWrappedLocation(x, y)
    val pixelValue = matrix[pixelLocation]
    matrix[pixelLocation] = pixelValue xor 1

    val pixelErased = matrix[pixelLocation] == 0
    return pixelErased
  }

  fun getPixel(x: Int, y: Int): Boolean {
    val pixelLocation = findWrappedLocation(x, y)
    val pixelValue = matrix[pixelLocation]
    return pixelValue == 1
  }

  fun clear() {
    matrix.fill(0)
  }

  private fun findWrappedLocation(x: Int, y: Int): Int {
    val wrappedX = when {
      x >= COLUMNS -> x - COLUMNS
      x < 0 -> x + COLUMNS
      else -> x
    }
    val wrappedY =  when {
      y >= ROWS -> y - ROWS
      y < 0 -> y + ROWS
      else -> y
    }
    return wrappedX + (wrappedY * COLUMNS)
  }
}
