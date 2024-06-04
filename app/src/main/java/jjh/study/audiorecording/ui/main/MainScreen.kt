package jjh.study.audiorecording.ui.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.orhanobut.logger.Logger
import jjh.study.audiorecording.R
import jjh.study.audiorecording.ui.home.HomeScreen


@Preview(showBackground = true)
@Composable
fun MainScreen(
  mainViewModel: MainViewModel = hiltViewModel(),
) {
  Image(
    modifier = Modifier.fillMaxSize(),
    painter = painterResource(id = R.drawable.background),
    contentDescription = "background"
  )

  HomeScreen(
    Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
    onClick = {
      Logger.e("클릭")
      mainViewModel.stopRecording()
    }
  )
}