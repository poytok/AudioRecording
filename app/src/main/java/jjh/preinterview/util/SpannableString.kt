package jjh.preinterview.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle


@Composable
fun SpannableString(
  modifier: Modifier = Modifier,
  fullText: String,
  spannableText: String,
  style: SpanStyle,
  textStyle: TextStyle,
) {
  val startIndex = fullText.indexOf(spannableText)
  if (startIndex == -1)
    return

  val startString = fullText.substring(0, startIndex)
  val appendString = fullText.substring(startIndex + spannableText.length)

  val annotatedString = buildAnnotatedString {
    append(startString)
    withStyle(style = style) {
      append(spannableText)
    }
    if (appendString.isNotEmpty()) {
      append(appendString)
    }
  }

  Text(
    modifier = modifier,
    text = annotatedString,
    style = textStyle,
  )
}

@Composable
fun SpannableString(
  fullText: String,
  spannableStartIndex: Int,
  spannableEndIndex: Int,
  style: SpanStyle,
  textStyle: TextStyle,
) {

  val startString = fullText.substring(0, spannableStartIndex)
  val spannableText = fullText.substring(spannableStartIndex, spannableEndIndex)
  val appendString = fullText.substring(spannableEndIndex - spannableStartIndex)

  val annotatedString = buildAnnotatedString {
    append(startString)
    withStyle(style = style) {
      append(spannableText)
    }
    if (fullText.length >= spannableEndIndex) {
      append(appendString)
    }
  }

  Text(
    text = annotatedString,
    style = textStyle,
  )
}
