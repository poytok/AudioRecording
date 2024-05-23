package jjh.study.audiorecording.util

import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtLoggingLevel
import ai.onnxruntime.OrtSession
import android.content.Context

class FROnnxMobileNet(private val context: Context) {
  //안드로이드 가속화
  private var enableNNAPI: Boolean = false

  //오닉스 런타임 (ONNX)
  private var ortEnv: OrtEnvironment? = null
  private var ortSession: OrtSession? = null

  init {
    ortEnv = OrtEnvironment.getEnvironment(OrtLoggingLevel.ORT_LOGGING_LEVEL_FATAL)
    ortSession = createOrtSession()
  }

  private fun createOrtSession(): OrtSession? {
    val so = OrtSession.SessionOptions()
    so.use {
      // Set to use 2 intraOp threads for CPU EP
      so.setIntraOpNumThreads(2)

      if (enableNNAPI)
        so.addNnapi()

      return null // ortEnv?.createSession(readModel(), so)
    }
  }

//  private fun readModel(): ByteArray {
//    val res = CovidApplication.instance.resources
//    return res.openRawResource(R.raw.mobileface).readBytes()
//  }
}