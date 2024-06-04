package jjh.preinterview.ui.record

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import jjh.preinterview.R
import jjh.preinterview.ui.theme.AudioRecordingTheme
import jjh.preinterview.util.BaseButton

@Composable
fun RecordScreen(
  recordTime: Int = 60,
  title: String = "간단한 자기 소개를 해 주세요",
) {
  var progress by remember { mutableFloatStateOf(0f) }
  Column(
    modifier = Modifier
      .padding(horizontal = 24.dp)
      .background(color = Color.White),
  ) {
    Spacer(modifier = Modifier.height(62.dp))
    Row(verticalAlignment = Alignment.CenterVertically) {
      TimeRequiredText() // 소요시간
      Spacer(modifier = Modifier.width(6.dp))
      TimerText(timerText = "${recordTime}초") // 60초
    }

    RecordTitle(title)

    Spacer(modifier = Modifier.height(21.dp))
    // 중앙 타이머
    TimerCircle(
      modifier = Modifier.fillMaxWidth(),
      brush = Brush.horizontalGradient(listOf(Color(0xff0064F2), Color(0xffF496BD))),
      totalProgress = recordTime.toFloat(),
      progress = progress,
    )

    Spacer(modifier = Modifier.weight(1f))
    BaseButton(
      modifier = Modifier.padding(vertical = 16.dp),
      text = "바로 시작하기"
    ) {/* TODO: OnClick */ }
  }
}

@Composable
fun TimeRequiredText() {
  Text(
    modifier = Modifier
      .background(color = Color(0xff0064F2), shape = RoundedCornerShape(10.dp))
      .padding(horizontal = 8.dp, vertical = 4.dp),
    text = "소요시간",
    style = TextStyle(
      color = Color(0xffffffff),
      fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_regular)),
      fontWeight = FontWeight.W400,
      fontSize = 12.sp,
      lineHeight = 12.sp,
      letterSpacing = (-0.05).em
    )
  )
}

@Composable
fun TimerText(
  timerText: String = "60초",
) {
  Text(
    text = timerText,
    style = TextStyle(
      color = Color(0xff69777E),
      fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_regular)),
      fontWeight = FontWeight.W400,
      fontSize = 12.sp,
      lineHeight = 12.sp,
      letterSpacing = (-0.05).em
    )
  )
}

@Composable
fun RecordTitle(
  title: String,
) {
  Text( // 간단한 자기 소래를 해주세요
    modifier = Modifier
      .fillMaxWidth()
      .padding(top = 6.dp),
    text = title,
    style = TextStyle(
      color = Color(0xff252E33),
      fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_regular)),
      fontWeight = FontWeight.W400,
      fontSize = 26.sp,
      lineHeight = 12.sp,
      letterSpacing = (-0.05).em
    )
  )
}

@Composable
fun TimerCircle(
  modifier: Modifier,
  brush: Brush,
  totalProgress: Float,
  progress: Float,
) {
  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center
  ) {
    Canvas(
      modifier = Modifier.size(240.dp),
      onDraw = {
        drawCircle(
          color = Color(0x4DB7C5C8),
          style = Stroke(
            width = 12f,
          ),
        )
        drawArc(
          brush,
          startAngle = 270f,
          sweepAngle = progress * (360 / totalProgress),
          useCenter = false,
          style = Stroke(12f),
        )
      }
    )

    Column {
      Text(text = "테스트지롱")
      Text(text = "테스트지롱")
    }
  }
}

@Composable
fun GradientCircle(
  modifier: Modifier = Modifier,
  stroke: Stroke = Stroke(width = 12f),
  backgroundColor: Color = Color(0x4DB7C5C8),
  gradientBrush: Brush,
  gradientStartAngle: Float = 270f,
  totalProgress: Float,
  progress: Float,
) {
  Canvas(
    modifier = modifier.size(240.dp),
    onDraw = {

      // 배경
      drawCircle(
        color = backgroundColor,
        style = stroke,
      )

      // 그라데이션
      drawArc(
        brush = gradientBrush,
        startAngle = gradientStartAngle,
        sweepAngle = progress * (360 / totalProgress),
        useCenter = false,
        style = stroke,
      )
    }
  )
}

@Preview(showBackground = true)
@Composable
private fun GradientCirclePreview() {
  val totalProgress = 60f
  val progress = 30f

  GradientCircle(
    modifier = Modifier.padding(30.dp),
    gradientBrush = Brush.horizontalGradient(listOf(Color(0xff0064F2), Color(0xffF496BD))),
    totalProgress = totalProgress,
    progress = progress,
  )

}


@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
private fun RecordScreenPreview() {
  AudioRecordingTheme {
    RecordScreen()
  }
}