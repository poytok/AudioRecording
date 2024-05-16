package jjh.study.audiorecording.ui.select_question

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jjh.study.audiorecording.MainViewModel
import jjh.study.audiorecording.R
import jjh.study.audiorecording.util.BaseButton
import jjh.study.audiorecording.util.SpannableString


@Composable
fun SelectQuestionScreen(
  modifier: Modifier = Modifier,
  mainViewModel: MainViewModel = MainViewModel(),
) {
  val state = mainViewModel.state
  Column(
    modifier = modifier
      .padding(horizontal = 24.dp),
    verticalArrangement = Arrangement.SpaceBetween
  ) {
    Spacer(modifier = Modifier.height(62.dp))
    SelectQuestionTopText()
    Spacer(modifier = Modifier.height(40.dp))

    state.forEachIndexed { index, questionModel ->
      SelectQuestionCheckbox(
        checked = questionModel.checked,
        onCheckedChange = { mainViewModel.onCheckedChange(index, it) },
        text = questionModel.title
      )
      Spacer(modifier = Modifier.height(16.dp))
    }

    Spacer(modifier = Modifier.weight(1f))
    BaseButton(
      modifier = Modifier.padding(vertical = 16.dp),
      text = "연습하러가기"
    ) {

    }
  }
}

@Composable
fun SelectQuestionTopText() {
  val textStyle = TextStyle(
    letterSpacing = (-0.5).sp,
    lineHeight = 35.84.sp,
    fontSize = 28.sp,
    fontStyle = FontStyle(R.font.spoqa_han_sans_neo_medium),
  )

  SpannableString(
    fullText = "어떤 질문을\n연습해 볼까요?",
    spannableText = "질문",
    style = SpanStyle(color = Color(0xff0064F2), fontWeight = FontWeight.W700),
    textStyle = textStyle,
  )
}

@Composable
fun SelectQuestionCheckbox(
  text: String = "",
  checked: Boolean = false,
  onCheckedChange: (Boolean) -> Unit = {},
) {

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(color = Color(0xFFF5F8F9), shape = RoundedCornerShape(8.dp))
      .clickable { onCheckedChange(checked.not()) },
    verticalAlignment = Alignment.CenterVertically,
  ) {
    val iconId = if (checked) {
      R.drawable.logo_toss
    } else {
      R.drawable.logo_toss_uncheck
    }
    Image(
      modifier = Modifier
        .padding(horizontal = 12.dp),
      painter = painterResource(id = iconId),
      contentDescription = "checked"
    )

    Text(
      text = text,
      modifier = Modifier
        .padding(vertical = 16.dp)
        .padding(end = 12.dp)
    )

  }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun SelectQuestionCheckboxPreview() {
  SelectQuestionCheckbox(text = "텍스트다 우하하핰")
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun SelectQuestionPreviewScreen() {
  SelectQuestionScreen()
}