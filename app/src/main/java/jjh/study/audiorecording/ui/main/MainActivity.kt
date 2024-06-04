package jjh.study.audiorecording.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jjh.study.audiorecording.R
import jjh.study.audiorecording.ui._nav.AudioRecordNavHost
import jjh.study.audiorecording.ui._nav.Screens


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val mainViewModel: MainViewModel by viewModels()

  @SuppressLint("MissingPermission")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)




    setContent {
      val navController = rememberNavController()

//      AudioRecordingTheme {
        // Background
        Image(
          modifier = Modifier.fillMaxSize(),
          painter = painterResource(id = R.drawable.background),
          contentDescription = "background"
        )

        AudioRecordNavHost(
          modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
          navHostController = navController,
          startDestination = Screens.Login.name,
          mainViewModel = mainViewModel
        )
//      }
    }
  }

  private val recordPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
    if (it) {
      // 오디오 녹음 시작
      mainViewModel.startRecording()
    }
  }
}