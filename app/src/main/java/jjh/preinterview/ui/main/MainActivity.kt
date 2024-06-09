package jjh.preinterview.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import jjh.preinterview.ui._nav.AudioRecordNavHost
import jjh.preinterview.ui._nav.MainScreens


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val mainViewModel: MainViewModel by viewModels()

  @SuppressLint("MissingPermission")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)

    setContent {
      val navController = rememberNavController()
      AudioRecordNavHost(
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
        navHostController = navController,
        startDestination = MainScreens.LOGIN_SCREEN.name,
      )
    }
  }

  private val recordPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
    if (it) {
      // 오디오 녹음 시작
      mainViewModel.startRecording()
    }
  }
}