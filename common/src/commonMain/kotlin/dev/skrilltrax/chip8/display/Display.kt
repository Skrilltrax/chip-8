@file:Suppress("FunctionName")
package dev.skrilltrax.chip8.display

class Display {

  val displayMatrix = DisplayMatrix.createEmpty()

  fun clear() {
    displayMatrix.clear()
  }

  fun setPixel(x: Int, y: Int): Boolean {
    return displayMatrix.setPixel(x, y)
  }

  companion object {
    const val COLUMNS = 64
    const val ROWS = 32
    const val SCALE = 100
  }
}