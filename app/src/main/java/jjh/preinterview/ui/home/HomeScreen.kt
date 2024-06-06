package jjh.preinterview.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import jjh.preinterview.R
import jjh.preinterview.util.DrawableDirection
import jjh.preinterview.util.DrawableText
import jjh.preinterview.util.Spacer
import jjh.preinterview.util.drawColoredShadow


@Composable
fun HomeScreen(
  modifier: Modifier = Modifier,
  homeViewModel: HomeViewModel,
) {
  val state = homeViewModel.homeUiState.value
  val scrollState = rememberScrollState()

  Column(
    modifier = Modifier
      .fillMaxSize()
      .verticalScroll(scrollState)
      .padding(bottom = WindowInsets.safeDrawing.getBottom(density = LocalDensity.current).dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    80.Spacer()
    Image(
      painter = painterResource(id = R.drawable.main_logo),
      contentDescription = "MainLogo",
    )
    72.Spacer()


    InterviewQuestion(
      selectedOccupationalString = state.selectedOccupational.string
    ) // 면접질문 Row

    15.Spacer()

    QuestionList(
      modifier = modifier,
      questionTitles = state.questionTitles,
      selectedTitle = state.selectedQuestion,
      onClick = { homeViewModel.setQuestion(it) }
    ) // 기본, 경험, 가치관, 상황
//
    QuestionContentList(
      modifier = Modifier.padding(horizontal = 24.dp),
      questions = state.questionTitles,
      questionContent = state.questionContentList
    )
  }
}


@Composable
private fun InterviewQuestion(
  modifier: Modifier = Modifier,
  selectedOccupationalString: String,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 24.dp),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    Text(
      text = stringResource(id = R.string.interview_question),
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_bold)),
        fontSize = 20.sp,
        fontWeight = FontWeight.W700,
        lineHeight = 24.04.sp,
        letterSpacing = (-0.05).em,
        color = Color(0xff252E33)
      )
    ) // Title 면접질문

    DrawableText(
      drawableDirection = DrawableDirection.END,
      paintResourceId = R.drawable.arrow_bottom
    ) {
      Text(
        modifier = Modifier.padding(8.dp),
        text = selectedOccupationalString,
      )
    } // Occupational String >> 개발 및 데이터
  }
}

@Composable
fun QuestionList(
  modifier: Modifier = Modifier,
  questionTitles: List<Question>,
  selectedTitle: Question,
  onClick: (Question) -> Unit,
) {
  val scrollState = rememberScrollState()

  Row(
    modifier = Modifier
      .padding(horizontal = 24.dp)
      .fillMaxWidth()
      .horizontalScroll(state = scrollState)
  ) {
    questionTitles.forEach {
      QuestionView(
        modifier = Modifier
          .padding(end = 8.dp)
          .weight(1f),
        question = it,
        isSelected = it == selectedTitle,
        onClick = onClick
      )
    }
  }
}

@Composable
fun QuestionView(
  modifier: Modifier = Modifier,
  question: Question,
  isSelected: Boolean,
  onClick: (Question) -> Unit,
) {

  val (textColor, backgroundColor, strokeWith) = if (isSelected) {
    Triple(Color(0xffffffff), Color(0xff0064F2), 0f)
  } else {
    Triple(Color(0xff69777e), Color(0xffffffff), 1f)
  }

  val shape = RoundedCornerShape(percent = 50)

  Box(
    modifier = modifier
      .background(shape = shape, color = backgroundColor)
      .border(width = strokeWith.dp, color = Color(0xffB7C5C8), shape = shape)
      .clickable { onClick(question) },
  ) {
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 12.dp),
      text = question.string,
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_regular)),
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 16.sp,
        letterSpacing = (-0.05).em,
        color = textColor,
        textAlign = TextAlign.Center
      )
    )
  }
}

@Composable
fun QuestionContentList(
  modifier: Modifier = Modifier,
  questions: List<Question>,
  questionContent: Map<Question, List<String>>,
) {

  Column {
    questions.forEach { question ->
      30.Spacer()
      QuestionContentTitle(
        modifier = modifier,
        questionContentTitle = question.string,
      )
      12.Spacer()

      questionContent[question]?.forEach { questionContent ->
        10.Spacer()
        QuestionContent(
          modifier = modifier,
          questionContent = questionContent,
        )
      }
    }
  }
}

// questionContentList
@Composable
fun QuestionContentTitle(
  modifier: Modifier = Modifier,
  questionContentTitle: String,
) {
  Text(
    modifier = modifier,
    text = questionContentTitle,
    style = TextStyle(
      fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_bold)),
      fontSize = 18.sp,
      fontWeight = FontWeight.W700,
      lineHeight = 18.sp,
      letterSpacing = (-0.05).em,
      textAlign = TextAlign.Left
    )
  )
}

@Composable
fun QuestionContent(
  modifier: Modifier = Modifier,
  questionContent: String,
) {

  Box(
    modifier = modifier
      .fillMaxWidth()
      .drawColoredShadow(
        color = Color(0x990064F2),
        borderRadius = 12.dp,
        alpha = 0.3f
      )
      .background(color = Color.White, shape = RoundedCornerShape(12.dp))
  ) {
    Text(
      modifier = Modifier
        .padding(horizontal = 20.dp, vertical = 16.dp),
      text = questionContent,
    )
  }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
  HomeScreen(homeViewModel = HomeViewModel())
}