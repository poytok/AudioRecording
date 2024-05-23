package jjh.study.audiorecording.ui.main

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import jjh.study.audiorecording.ui.theme.AudioRecordingTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  private var audioRecord: AudioRecord? = null
  private val mainViewModel: MainViewModel by viewModels()

  @SuppressLint("MissingPermission")
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val sampleRate = 8000
    val channelConfig = AudioFormat.CHANNEL_IN_MONO
    val audioFormat = AudioFormat.ENCODING_PCM_16BIT

    mainViewModel.state

    val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

//    audioRecord =
//      AudioRecord.Builder()
//        .setAudioSource(AudioSource.VOICE_RECOGNITION)
//        .setAudioFormat(
//          AudioFormat.Builder()
//            .setSampleRate(sampleRate)
//            .setChannelMask(AudioFormat.CHANNEL_IN_MONO)
//            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
//            .build()
//        ).build()

//    AudioRecord(
//      AudioSource.MIC,
//      sampleRate,
//      channelConfig,
//      audioFormat,
//      bufferSize,
//    )


    setContent {
      AudioRecordingTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background,
        ) {


        }
      }
    }
  }


}