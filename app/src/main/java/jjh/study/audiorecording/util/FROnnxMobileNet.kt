package jjh.study.audiorecording.util

import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtLoggingLevel
import ai.onnxruntime.OrtSession
import android.content.Context
import com.orhanobut.logger.Logger
import jjh.study.audiorecording.R

class FROnnxMobileNet(private val context: Context) {
  //안드로이드 가속화
  private var enableNNAPI: Boolean = false

  //오닉스 런타임 (ONNX)
  private var ortEnv: OrtEnvironment? = null
  private var ortSession: OrtSession? = null

  init {
    ortEnv = OrtEnvironment.getEnvironment(OrtLoggingLevel.ORT_LOGGING_LEVEL_FATAL)
    ortSession = createOrtSession()


    Logger.e(
      """ 
    1. ${ortSession?.metadata}
    2. ${ortSession?.inputInfo}
    3. ${ortSession?.inputNames}
    4. ${ortSession?.numInputs}
    """.trimIndent()
    )
//    ortSession?.run()
  }

  private fun createOrtSession(): OrtSession? {
    val so = OrtSession.SessionOptions()
    so.use {
      // Set to use 2 intraOp threads for CPU EP
      so.setIntraOpNumThreads(2)

      if (enableNNAPI)
        so.addNnapi()

      return ortEnv?.createSession(readModel(), so)
    }
  }

  private fun readModel(): ByteArray {
    val res = context.resources
    return res.openRawResource(R.raw.marble_vad_8k_v3).readBytes()
  }
}