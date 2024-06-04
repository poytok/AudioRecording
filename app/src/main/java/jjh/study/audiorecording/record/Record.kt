package jjh.study.audiorecording.record

import android.annotation.SuppressLint
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import com.orhanobut.logger.Logger
import jjh.study.audiorecording.tensor.TensorConvertor
import jjh.study.audiorecording.tensor.VoiceActivityDetection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.stateIn
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

  @SuppressLint("MissingPermission")
  private fun setAudioRecord() {
    audioRecord = AudioRecord(
      MediaRecorder.AudioSource.MIC,
      RecordSetting.SAMPLE_RATE_IN_HZ,
      RecordSetting.CHANNEL_CONFIG,
      RecordSetting.AUDIO_FORMAT,
      RecordSetting.minBufferSize
    )
  }

  val data = mutableListOf<Float>()

  suspend fun startRecording(
    viewModelScope: CoroutineScope,
  ) {
    if (audioRecord == null)
      setAudioRecord()

    isRecording = true
    audioRecord?.startRecording()

    val shortArray = ShortArray(8000)
    while (isRecording) {
      val bytesRead = audioRecord?.read(shortArray, 0, shortArray.size) ?: 0
      if (bytesRead > 0) {
        Log.d("AudioCapture", "Received audio data: ${shortArray.joinToString(" ")}")
        val floatArray = TensorConvertor.shortArrayToFloatArray(shortArray).toTypedArray()
        viewModelScope.launch(Dispatchers.Default) {
          data.addAll(floatArray)
        }

        model.startModel(shortArray)
          .stateIn(viewModelScope)
          .collect {
            if (it > 0.5f) {
              Logger.e("$it >> ${shortArray.joinToString(" ")}")
            }
          }
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