package jjh.preinterview.ui.signup.nickname

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orhanobut.logger.Logger
import jjh.preinterview.R
import jjh.preinterview.ui.signup.SignupViewModel
import jjh.preinterview.util.Spacer
import jjh.preinterview.util.SpannableString
import jjh.preinterview.util.drawColoredShadow

@Composable
fun InputNicknameScreen(
  modifier: Modifier = Modifier,
  signupViewModel: SignupViewModel = hiltViewModel(),
  onNextButtonClick: () -> Unit = {},
) {
  val state = signupViewModel.state

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(Color.White)
  ) {

    32.Spacer()
    Title(modifier = Modifier.padding(horizontal = 24.dp))

    40.Spacer()
    NicknameInput(
      modifier = Modifier
        .fillMaxWidth()
        .padding(24.dp),
      text = state.nickname,
      onValueChangeListener = { signupViewModel.setNickname(it) }
    )

    Spacer(modifier = Modifier.weight(1f))
    BottomButton(
      isEnabled = state.nickname.isNotEmpty(),
      onNextButtonClick = {
        onNextButtonClick()
      }
    )
  }
}

@Composable
private fun Title(modifier: Modifier = Modifier) {
  val fullText = "면접 연습 시 사용할\n이름을 입력해 주세요"
  val spannableString = "이름"
  SpannableString(
    modifier = modifier,
    fullText = fullText,
    spannableText = spannableString,
    style = SpanStyle(
      fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_bold)),
      fontSize = 28.sp,
      fontWeight = FontWeight.W700,
      color = Color(0xff252E33),
    ),
    textStyle = TextStyle(
      fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_thin)),
      fontSize = 28.sp,
      fontWeight = FontWeight.W300,
      lineHeight = 35.84.sp,
      letterSpacing = (-0.05).em,
      textAlign = TextAlign.Left,
      color = Color(0xff252E33),
    )
  )
}

@Composable
fun NicknameInput(
  modifier: Modifier = Modifier,
  text: String = "",
  onValueChangeListener: (String) -> Unit = {},
) {

  TextField(
    modifier = modifier,
    singleLine = true,
    value = text,
    textStyle = TextStyle(
      fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_regular)),
      fontSize = 20.sp,
      fontWeight = FontWeight.W400,
      lineHeight = 26.4.sp,
      letterSpacing = (-0.05).em,
      textAlign = TextAlign.Left,
      color = Color(0xff000000)
    ),
    onValueChange = onValueChangeListener,
    trailingIcon = {
      if (text.isEmpty())
        return@TextField

      IconButton(onClick = { onValueChangeListener("") }) {
        Icon(
          painter = painterResource(id = R.drawable.ico_clear),
          contentDescription = "",
          tint = Color.Unspecified
        )
      }
    },
    placeholder = {
      Text(
        text = "닉네임(최대 12자)",
        style = TextStyle(
          fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_regular)),
          fontSize = 20.sp,
          fontWeight = FontWeight.W400,
          lineHeight = 26.4.sp,
          letterSpacing = (-0.05).em,
          textAlign = TextAlign.Left,
          color = Color(0xff9daab0)
        )
      )
    },
    colors = TextFieldDefaults.colors(
      focusedPlaceholderColor = Color.White,
      unfocusedContainerColor = Color.White,
      focusedContainerColor = Color.White
    )
  )
}

@Composable
private fun BottomButton(
  isEnabled: Boolean,
  onNextButtonClick: () -> Unit = {},
) {
  val textView: @Composable () -> Unit = {
    Text(
      modifier = Modifier.background(color = Color.Transparent),
      text = "확인",
      color = Color(0xffffffff),
      textAlign = TextAlign.Center,
      fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_bold)),
      fontWeight = FontWeight.W700,
      lineHeight = 23.76.sp,
      letterSpacing = (-0.05).em,
      fontSize = 18.sp
    )
  }

  if (!isEnabled) {
    TextButton(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp, horizontal = 24.dp)
        .height(52.dp)
        .background(
          color = Color(0xffCCE2E2),
          shape = RoundedCornerShape(8.dp)
        ),
      enabled = false,
      onClick = {},
    ) {
      textView()
    }
    return
  }

  TextButton(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 16.dp, horizontal = 24.dp)
      .height(52.dp)
      .drawColoredShadow(
        color = Color(0x9944C0C6),
        borderRadius = 1.dp,
        alpha = 0.7f
      )
      .background(
        brush = Brush.horizontalGradient(
          colors = listOf(Color(0xff3F99CB), Color(0xff47DCC1))
        ),
        shape = RoundedCornerShape(8.dp)
      ),
    onClick = {
      Logger.e("클릭쓰")
      onNextButtonClick()
    }
  ) {
    textView()
  }

}

@Preview(showBackground = true)
@Composable
private fun InputNickNameScreenPreview() {
  InputNicknameScreen()
}

@Preview(showBackground = true)
@Composable
private fun TitlePreview() {
  Title()
}


@Preview(showBackground = true)
@Composable
fun NicknameInputPreview(modifier: Modifier = Modifier) {
  NicknameInput()
}

@Preview(showBackground = true)
@Composable
private fun BottomButtonPreview() {
  Column {
    BottomButton(isEnabled = true)
    BottomButton(isEnabled = false)
  }
}