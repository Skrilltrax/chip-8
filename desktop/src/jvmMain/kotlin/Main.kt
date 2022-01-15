import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.skrilltrax.chip8.display.Render
import dev.skrilltrax.chip8.display.DisplayMatrix

fun main() = application {
  Window(onCloseRequest = ::exitApplication) {
    MaterialTheme {
      val displayMatrix = remember { DisplayMatrix.createEmpty() }
      SideEffect {
        displayMatrix.setPixel(14, 15)
        displayMatrix.setPixel(14, 16)
        displayMatrix.setPixel(14, 17)
        displayMatrix.setPixel(14, 18)
        displayMatrix.setPixel(14, 19)
        displayMatrix.setPixel(14, 20)
      }
      Render(Modifier.fillMaxSize(), displayMatrix, 20)
    }
  }
}