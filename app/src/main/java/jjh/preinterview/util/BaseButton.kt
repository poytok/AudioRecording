package jjh.preinterview.util

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import jjh.preinterview.R
import jjh.study.audiorecording.R

@SuppressLint("NewApi")
@Composable
fun BaseButton(
  modifier: Modifier = Modifier,
  text: String,
  onClick: () -> Unit,
) {
  TextButton(
    modifier = modifier
      .fillMaxWidth()
      .height(52.dp)
      .drawColoredShadow(
        color = Color(0x990064F2),
        borderRadius = 1.dp,
        alpha = 0.3f
      ),
//      .shadow(elevation = 14.dp, spotColor = Color(0x990064F2)),
    onClick = onClick,
    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff0064F2)),
    shape = RoundedCornerShape(8.dp),
  ) {
    Text(
      modifier = Modifier.background(color = Color.Transparent),
      text = text,
      color = Color(0xffffffff),
      textAlign = TextAlign.Center,
      fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_bold)),
      fontWeight = FontWeight.W700,
      lineHeight = 23.76.sp,
      letterSpacing = (-0.05).em,
      fontSize = 18.sp
    )
  }

}

@Preview(widthDp = 320, heightDp = 640, showBackground = true)
@Composable
private fun BaseButtonPreview() {
  BaseButton(modifier = Modifier, text = "테스트", {})
}