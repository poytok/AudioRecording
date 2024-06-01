package jjh.study.audiorecording.record

import android.media.AudioFormat
import android.media.AudioRecord

object RecordSetting {
  const val SAMPLE_RATE_IN_HZ = 8000

  const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO

  const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT

  val minBufferSize = AudioRecord.getMinBufferSize(
    SAMPLE_RATE_IN_HZ,
    CHANNEL_CONFIG,
    AUDIO_FORMAT
  )
}