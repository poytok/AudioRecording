package jjh.study.audiorecording.ui.main

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import dagger.hilt.android.AndroidEntryPoint
import jjh.study.audiorecording.R
import jjh.study.audiorecording.ui.home.HomeScreen
import jjh.study.audiorecording.ui.theme.AudioRecordingTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private var audioRecord: AudioRecord? = null
  private val mainViewModel: MainViewModel by viewModels()

  @SuppressLint("MissingPermission")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    WindowCompat.setDecorFitsSystemWindows(window, false)

    val sampleRate = 8000
    val channelConfig = AudioFormat.CHANNEL_IN_MONO
    val audioFormat = AudioFormat.ENCODING_PCM_16BIT

//    mainViewModel.state

    val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
//    FROnnxMobileNet(this)

//    val byteArray = ByteArray(1024) { 0 }
//
//    val data = VoiceActivityDetection(this, byteArray)
//
//    val ddd = data.run(FloatBuffer.allocate(1024))


    setContent {
//      AudioRecordingTheme {
//        Scaffold(
//          modifier = Modifier.fillMaxSize(),
//          containerColor = Color.Transparent,
//        ) { paddingValues ->
          Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background),
            contentDescription = "background"
          )

          HomeScreen(Modifier.windowInsetsPadding(WindowInsets.safeDrawing))
//        }
//      }
    }
  }

  @Preview
  @Composable
  private fun Main() {
    AudioRecordingTheme {
      Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
      ) { paddingValues ->
        Image(
          modifier = Modifier.fillMaxSize(),
          painter = painterResource(id = R.drawable.background),
          contentDescription = "background"
        )

        HomeScreen(
          Modifier.padding(paddingValues)
        )
      }
    }
  }

}