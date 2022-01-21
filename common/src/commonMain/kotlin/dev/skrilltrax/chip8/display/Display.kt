@file:Suppress("FunctionName")
package dev.skrilltrax.chip8.display

import kotlinx.coroutines.flow.*

class Display {

  private val displayMatrix = DisplayMatrix()
  private val _displayMatrixFlow: MutableStateFlow<DisplayMatrix> = MutableStateFlow(displayMatrix)
  val displayMatrixFlow = _displayMatrixFlow.asStateFlow()

  fun clear() {
    displayMatrix.clear()
  }

  fun setPixel(x: Int, y: Int): Boolean {
    val pixelErased = displayMatrix.setPixel(x, y)
    _displayMatrixFlow.update { displayMatrix }
    return pixelErased
  }

  fun getPixel(x: Int, y: Int): Boolean {
    return displayMatrix.getPixel(x, y)
  }

  fun getDisplayMatrixFlow(): Flow<DisplayMatrix> {
    return displayMatrixFlow
  }

  companion object {
    const val COLUMNS = 64
    const val ROWS = 32
    const val SCALE = 10
  }
}