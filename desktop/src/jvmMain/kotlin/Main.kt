import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.window.*
import dev.skrilltrax.chip8.Chip8
import java.awt.FileDialog
import java.io.File

fun main() = application {
  val state = rememberWindowState(placement = WindowPlacement.Maximized)

  Window(onCloseRequest = ::exitApplication, state = state) {
    MaterialTheme {
      var isFileSelected by remember { mutableStateOf(false) }
      var filePath by remember { mutableStateOf("") }
      val chip8 = remember { Chip8() }
      val windowWidth = LocalDensity.current.run { state.size.width.toPx() }
      val scale = (windowWidth / 64).toInt()

      if (isFileSelected) {
        SideEffect { chip8.loadRom(File(filePath).readBytes()) }

        chip8.Render(scale)
      } else {
        FileDialogScreen(modifier = Modifier.fillMaxSize()) {
          isFileSelected = true
          filePath = it
        }
      }
    }
  }
}

@Composable
fun FrameWindowScope.FileDialogScreen(modifier: Modifier = Modifier, onFileSelected: (String) -> Unit) {
  Box(modifier, contentAlignment = Alignment.Center) {
    Button(onClick = {
      val files = openFileDialog()
      onFileSelected(files.first().absolutePath)
    }) {
      Text(text = "Open File Picker")
    }
  }
}

fun FrameWindowScope.openFileDialog(): List<File> {
  val fileDialog = FileDialog(window).apply {
    isMultipleMode = false
    isVisible = true
  }
  return fileDialog.files.toList()
}
