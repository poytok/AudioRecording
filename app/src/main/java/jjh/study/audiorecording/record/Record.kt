package jjh.study.audiorecording.record

import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import com.orhanobut.logger.Logger
import jjh.study.audiorecording.tensor.VoiceActivityDetection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class Record(
  private val model: VoiceActivityDetection,
) {
  var audioRecord: AudioRecord? = null

  var isRecording = false
    private set

  init {
    setAudioRecord()
  }

  private fun setAudioRecord() {
    audioRecord = AudioRecord(
      MediaRecorder.AudioSource.MIC,
      RecordSetting.SAMPLE_RATE_IN_HZ,
      RecordSetting.CHANNEL_CONFIG,
      RecordSetting.AUDIO_FORMAT,
      RecordSetting.minBufferSize
    )
  }

  suspend fun startRecording() {
    if (audioRecord == null)
      setAudioRecord()

    isRecording = true
    audioRecord?.startRecording()

    val shortArray = ShortArray(8000)
    CoroutineScope(Dispatchers.IO).launch {
      while (isRecording) {
        delay(500L)
        CoroutineScope(Dispatchers.IO).async {

          val bytesRead = audioRecord?.read(shortArray, 0, shortArray.size) ?: 0
          if (bytesRead > 0) {
            Log.d("AudioCapture", "Received audio data: ${shortArray.joinToString(" ")}")
            model.startModel(shortArray).collectLatest {

              Logger.e("data >> $it")
            }
          }
        }.join()
      }
    }
  }

  fun stopRecording() {
    isRecording = false
    audioRecord?.stop()
    audioRecord?.release()
    audioRecord = null
  }
}