package jjh.study.audiorecording.util


import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtLoggingLevel
import ai.onnxruntime.OrtSession
import ai.onnxruntime.extensions.OrtxPackage
import android.content.Context
import jjh.study.audiorecording.R
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.nio.LongBuffer

private fun createIntTensor(env: OrtEnvironment, data: IntArray, shape: LongArray): OnnxTensor {
  return OnnxTensor.createTensor(env, IntBuffer.wrap(data), shape)
}

private fun createLongTensor(env: OrtEnvironment, data: LongArray, shape: LongArray): OnnxTensor {
  return OnnxTensor.createTensor(env, LongBuffer.wrap(data), shape)
}

private fun createFloatTensor(
  env: OrtEnvironment,
  data: FloatArray,
  shape: LongArray,
): OnnxTensor {
  return OnnxTensor.createTensor(env, FloatBuffer.wrap(data), shape)
}

private fun tensorShape(vararg dims: Long) = longArrayOf(*dims)


class VoiceActivityDetection(
  private val context: Context,
  private val modelBytes: ByteArray
) : AutoCloseable {
  private val session: OrtSession
  private val baseInputs: Map<String, OnnxTensor>

  private var enableNNAPI: Boolean = false

  // Load ONNX model
  private val env: OrtEnvironment = OrtEnvironment.getEnvironment(OrtLoggingLevel.ORT_LOGGING_LEVEL_FATAL)

  init {
    val sessionOptions = OrtSession.SessionOptions()
    sessionOptions.registerCustomOpLibrary(OrtxPackage.getLibraryPath())
    session = createOrtSession() // env.createSession(modelBytes, sessionOptions)

    val samplingRate = 8000L
    val windowLength = 64 * (samplingRate / 1000)
//    val windowLength: Long = 0.064 * samplingRate

    baseInputs = mapOf(
      "length" to createLongTensor(env, longArrayOf(windowLength), tensorShape(1)).apply {
      }
    )
  }

  private fun createOrtSession(): OrtSession {
    val so = OrtSession.SessionOptions()
    so.use {
      // Set to use 2 intraOp threads for CPU EP
      so.setIntraOpNumThreads(2)

      if (enableNNAPI)
        so.addNnapi()

//      return env.createSession(ByteArray(1024) { 0 }, so)
      return env.createSession(readModel(), so)
    }
  }

  private fun readModel(): ByteArray {
    val res = context.resources
    return res.openRawResource(R.raw.marble_vad_8k_v3).readBytes()
  }


  fun run(floatAudioData: FloatBuffer): Float {
    // Prepare input map
    val inputs = mutableMapOf<String, OnnxTensor>()
    baseInputs.toMap(inputs)

    val floatAudioTensor = OnnxTensor.createTensor(
      env,
      floatAudioData,
      tensorShape(1, floatAudioData.array().size.toLong()) // floatAudioData.size == windowLength
    )
    inputs["wav"] = floatAudioTensor

    // Run inference
    val outputs = session.run(inputs)

    // Parse outputs
    val speechScore = outputs.use {
      @Suppress("UNCHECKED_CAST")
      (outputs[0].value as Array<Array<Float>>)[0][0] // 내일 해야할거 얘 타입 변경 관련해서 어떻게 할건지
    }

    return speechScore
  }

  override fun close() {
    baseInputs.values.forEach {
      it.close()
    }
    session.close()
  }
}