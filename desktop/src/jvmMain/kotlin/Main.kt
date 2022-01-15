import dev.skrilltrax.chip8.App
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
  Window(onCloseRequest = ::exitApplication) {
    MaterialTheme {
      App()
    }
  }
}