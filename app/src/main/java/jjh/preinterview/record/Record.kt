package jjh.preinterview.record

import android.annotation.SuppressLint
import android.media.AudioRecord
import android.media.MediaRecorder
import jjh.preinterview.tensor.TensorConvertor
import jjh.preinterview.tensor.VoiceActivityDetection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Record(private val model: VoiceActivityDetection, ) {
  private var audioRecord: AudioRecord? = null
  private var isRecording = false

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

  suspend fun startRecording() {
    if (audioRecord == null)
      setAudioRecord()

    isRecording = true
    audioRecord?.startRecording()

    val shortArray = ShortArray(8000)
    while (isRecording) { // 녹음 중 일 때 루프가 돈다
      withContext(Dispatchers.Default) {
        // 녹음된 데이터를 short 타입으로 변경
        val bytesRead = audioRecord?.read(shortArray, 0, shortArray.size) ?: 0
        if (bytesRead > 0) {
          // PCM데이터 리스트 저장용
          val floatArray = TensorConvertor.shortArrayToFloatArray(shortArray)

          model.startModel(floatArray)

          // 녹음된 PCM Data
          data.addAll(floatArray.toTypedArray())
        }
        // Speech Score List

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