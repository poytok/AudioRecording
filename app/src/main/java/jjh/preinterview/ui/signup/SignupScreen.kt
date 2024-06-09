package jjh.preinterview.ui.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import jjh.preinterview.R
import jjh.preinterview.ui._nav.SignupNavHost
import jjh.preinterview.ui._nav.SignupScreens


@Composable
fun SignupScreen(
  modifier: Modifier = Modifier,
  userId: String? = null,
) {
  val navHostController = rememberNavController()

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(Color.White)
  ) {
    HeaderLayout(onBackClickListener = { navHostController.navigateUp() })
    SignupNavHost(
      navHostController = navHostController,
      startDestination = SignupScreens.INPUT_NICKNAME.name,
      userId = userId,
    )
  }
}

@Composable
private fun HeaderLayout(
  onBackClickListener: () -> Unit = {},
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .height(50.dp)
      .padding(horizontal = 24.dp)
  ) {
    Image(
      modifier = Modifier
        .fillMaxHeight()
        .padding(start = 3.dp, end = 20.dp)
        .clickable { onBackClickListener() },
      painter = painterResource(id = R.drawable.prev_arrow),
      contentDescription = ""
    )

    Text(
      modifier = Modifier.align(Alignment.CenterVertically),
      text = "회원가입",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_regular)),
        fontSize = 18.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 50.sp,
        textAlign = TextAlign.Left
      )
    )
  }
}

@Preview
@Composable
private fun HeaderLayoutScreen() {
  HeaderLayout()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignupScreenPreview() {
  SignupScreen()
}