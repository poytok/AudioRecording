package jjh.preinterview.util

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun OneButtonDialog(
  modifier: Modifier = Modifier,
  title: String? = null,
  text: String,
  onDismissRequest: (() -> Unit)? = null,
  confirmText: String = "확인",
  onConfirmClickListener: () -> Unit,
) {
  AlertDialog(
    modifier = modifier,
    title = { title?.let { Text(text = it) } },
    text = { Text(text = text) },
    onDismissRequest = { onDismissRequest?.invoke() },
    confirmButton = { TextButton(onClick = onConfirmClickListener) { Text(text = confirmText) } }
  )
}

@Composable
fun CustomDialog(
  modifier: Modifier = Modifier,
  title: String,
  text: String,
  onDismissRequest: (() -> Unit)? = null,
  confirmText: String = "",
  confirmClickListener: (() -> Unit)? = null,
) {


  AlertDialog(
    modifier = modifier,
    title = { Text(text = title) },
    text = { Text(text = text) },
    onDismissRequest = { onDismissRequest?.invoke() },
    confirmButton = {
      Text(
        modifier = Modifier.clickable { },
        text = "Confirm"
      )
    },
    dismissButton = {
      Text(
        modifier = Modifier.clickable { },
        text = "Dismiss"
      )
    }
  )
}

@Preview
@Composable
private fun OneButtonDialogPreview() {
  OneButtonDialog(text = "텍스트") {}
}