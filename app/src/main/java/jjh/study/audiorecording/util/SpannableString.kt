package jjh.study.audiorecording.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle


@Composable
fun SpannableString(
  fullText: String,
  spannableText: String,
  style: SpanStyle,
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

  Text(text = annotatedString)
}
