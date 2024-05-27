package jjh.study.audiorecording.util

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Double.Spacer() {
  androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(this.dp))
}

@Composable
fun Int.Spacer() {
  androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(this.dp))
}

@Composable
fun Float.Spacer() {
  androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(this.dp))
}