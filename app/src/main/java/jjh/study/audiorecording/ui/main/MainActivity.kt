package jjh.study.audiorecording.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import jjh.study.audiorecording.ui.theme.AudioRecordingTheme


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private val mainViewModel: MainViewModel by viewModels()

  @SuppressLint("MissingPermission")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)

    // read sound file
    // val soundFileByteArray = Model.readSoundFile(resources)
    // mainViewModel.startModel(soundFileByteArray)
    recordPermission.launch(android.Manifest.permission.RECORD_AUDIO)

    setContent {
      AudioRecordingTheme {
        MainScreen(mainViewModel)
      }
    }
  }

  private val recordPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
    if (it) {
      // 오디오 녹음 시작
      mainViewModel.startRecording()
    }
  }

}