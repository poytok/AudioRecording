package jjh.preinterview.util

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource


@Composable
fun DrawableText(
  modifier: Modifier = Modifier,
  drawableDirection: DrawableDirection,
  @DrawableRes paintResourceId: Int,
  text: @Composable () -> Unit,
) {
  when (drawableDirection) {
    DrawableDirection.START -> DrawableStart(modifier, paintResourceId, text)
    DrawableDirection.END -> DrawableEnd(modifier, paintResourceId, text)
    DrawableDirection.TOP -> DrawableTop(modifier, paintResourceId, text)
    DrawableDirection.BOTTOM -> DrawableBottom(modifier, paintResourceId, text)
  }
}

@Composable
private fun DrawableStart(
  modifier: Modifier = Modifier,
  @DrawableRes paintResourceId: Int,
  text: @Composable () -> Unit,
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    Image(painter = painterResource(id = paintResourceId), contentDescription = "")
    text()
  }
}

@Composable
private fun DrawableEnd(
  modifier: Modifier = Modifier,
  @DrawableRes paintResourceId: Int,
  text: @Composable () -> Unit,
) {
  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically
  ) {
    text()
    Image(painter = painterResource(id = paintResourceId), contentDescription = "")
  }
}

@Composable
private fun DrawableTop(
  modifier: Modifier = Modifier,
  @DrawableRes paintResourceId: Int,
  text: @Composable () -> Unit,
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Image(painter = painterResource(id = paintResourceId), contentDescription = "")
    text()
  }
}

@Composable
private fun DrawableBottom(
  modifier: Modifier = Modifier,
  @DrawableRes paintResourceId: Int,
  text: @Composable () -> Unit,
) {
  Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    text()
    Image(painter = painterResource(id = paintResourceId), contentDescription = "")
  }
}

enum class DrawableDirection {
  START, END, TOP, BOTTOM
}