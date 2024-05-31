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
import com.launchdarkly.eventsource.ConnectStrategy
import com.launchdarkly.eventsource.EventSource
import com.launchdarkly.eventsource.background.BackgroundEventSource
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import jjh.study.audiorecording.R
import jjh.study.audiorecording.service.SseEventHandler
import jjh.study.audiorecording.ui.home.HomeScreen
import jjh.study.audiorecording.ui.theme.AudioRecordingTheme
import jjh.study.audiorecording.util.VoiceActivityDetection
import java.io.ByteArrayInputStream
import java.net.URI
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.util.concurrent.TimeUnit


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

    val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
//    FROnnxMobileNet(this)

    // init model
    val data = VoiceActivityDetection(readModel())

    // read sound file
    val soundFileByteArray = readSoundFile()

    val floatArray = FloatArray(soundFileByteArray.size / 4)
    val `is` = ByteArrayInputStream(soundFileByteArray)
    `is`.skip(58)
    val byteArray = ByteArray(4)
    var index = 0
    val length = 512

    `is`.use {
      while (`is`.read(byteArray) != -1) {
        val shortValue = ByteBuffer.wrap(byteArray).order(ByteOrder.LITTLE_ENDIAN).getFloat()
        floatArray[index++] = shortValue
      }
    }


    var offset = 0
    while (offset < floatArray.size) {

      val floatBuffer = if (offset + length > floatArray.size) {
        break
//        FloatBuffer.wrap(FloatArray(512) { floatArray.getOrNull(offset + it) ?: 0f }, 0, 512)
      } else {
        FloatBuffer.wrap(floatArray, offset, length)
      }

      val ddd = data.run(floatBuffer)
      Logger.e("DDDD >> $ddd")
      offset += length
    }
//    test()

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

  private fun getByteBuffer(
    position: Int,
    readByteSize: Int,
    soundFileByteArray: ByteArray,
  ): ByteBuffer {
    return if (position + readByteSize > soundFileByteArray.size) {
      val byteArray = ByteArray(readByteSize) { soundFileByteArray.getOrNull(it) ?: 0 }
      ByteBuffer.wrap(byteArray, 0, readByteSize)
    } else {
      ByteBuffer.wrap(soundFileByteArray, position, readByteSize)
    }
  }

  private fun getShortArray(): MutableList<FloatArray> {
    // 한 번에 읽을 바이트 수
    val bufferSize = 1024

    val floatArrayList = mutableListOf<FloatArray>()

    val byteArray = resources.openRawResource(R.raw.sample_8k).readBytes()

    val byteBuffer = ByteBuffer.wrap(byteArray)
    while (byteBuffer.hasRemaining()) {
      val buffer = if (byteBuffer.remaining() >= bufferSize) {
        ByteArray(bufferSize)
      } else {
        break
        // ByteArray(byteBuffer.remaining())
      }
      byteBuffer.get(buffer)

      val shortArray = ShortArray(buffer.size / 2)
      val shortBuffer = ByteBuffer.wrap(buffer)
      shortBuffer.order(ByteOrder.LITTLE_ENDIAN)
      for (i in shortArray.indices) {
        shortArray[i] = shortBuffer.short
      }

      // Short 배열 리스트에 추가

      floatArrayList.add(FloatArray(shortArray.size) { shortArray[it].toFloat() / 32768 })
    }

    return floatArrayList
  }

  private fun readModel(): ByteArray {
    return resources.openRawResource(R.raw.marble_vad_8k_v3).readBytes()
  }

  private fun readSoundFile(): ByteArray {
    return resources.openRawResource(R.raw.sample_8k).readBytes()
  }

  private fun test() {
    val sseEventHandler = SseEventHandler()
    val baseUrl = "https://d1c4-112-155-175-197.ngrok-free.app"
    val path = "/analyze/sse"

    val eventSourceBuilder = EventSource.Builder(
      ConnectStrategy
        .http(URI.create(baseUrl + path))
        .connectTimeout(3, TimeUnit.SECONDS)
        // 최대 연결 유지 시간을 설정, 서버에 설정된 최대 연결 유지 시간보다 길게 설정
        .readTimeout(600, TimeUnit.SECONDS)
    )

    BackgroundEventSource
      .Builder(sseEventHandler, eventSourceBuilder)
      .build()
      .start()
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