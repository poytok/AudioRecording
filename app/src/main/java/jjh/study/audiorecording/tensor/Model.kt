package jjh.study.audiorecording.tensor

import android.content.res.Resources
import jjh.study.audiorecording.R

object Model {
  fun readModel(resources: Resources): ByteArray {
    return resources.openRawResource(R.raw.vad_model_softmax).readBytes()
  }

  fun readSoundFile(resources: Resources): ByteArray {
    return resources.openRawResource(R.raw.sample_8k).readBytes()
  }
}