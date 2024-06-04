package jjh.study.audiorecording.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jjh.study.audiorecording.R
import jjh.study.audiorecording.util.Spacer


@Preview(showBackground = true)
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
  Column(modifier.fillMaxSize()) {
    136.Spacer()
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
      textAlign = TextAlign.Center,
      text = "AI와 함께하는 떨림 없는 면접 준비",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_regular)),
        fontSize = 18.sp,
        lineHeight = 23.76.sp,
        color = Color(0xff000000),
      )
    ) // AI와 함께하는 떨림 없는 면접 준비

    16.Spacer()

    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
      textAlign = TextAlign.Center,
      text = "미리면접",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_regular)),
        fontSize = 30.sp,
        lineHeight = 39.6.sp,
        color = Color(0xff000000),
      )
    ) // 미리면접

    25.Spacer()

    Image(
      modifier = Modifier
        .size(80.dp)
        .align(Alignment.CenterHorizontally),
      painter = painterResource(id = R.drawable.main_logo),
      contentDescription = ""
    ) // Logo


  }
}