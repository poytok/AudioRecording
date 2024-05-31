package jjh.study.audiorecording.util


import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtLoggingLevel
import ai.onnxruntime.OrtSession
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
  modelBytes: ByteArray
) : AutoCloseable {
  private val session: OrtSession
  private val baseInputs: Map<String, OnnxTensor>

  private var enableNNAPI: Boolean = false

  // Load ONNX model
  private val env: OrtEnvironment = OrtEnvironment.getEnvironment(OrtLoggingLevel.ORT_LOGGING_LEVEL_FATAL)

  init {
    val sessionOptions = OrtSession.SessionOptions()
//    sessionOptions.registerCustomOpLibrary(OrtxPackage.getLibraryPath())
    session = env.createSession(modelBytes, sessionOptions) // createOrtSession()

    baseInputs = mapOf("length" to createLongTensor(env, longArrayOf(WINDOW_LENGTH), tensorShape(1)))
  }


  fun run(floatAudioData: FloatBuffer): Float {
    // Prepare input map
    val inputs = mutableMapOf<String, OnnxTensor>()
    baseInputs.toMap(inputs)

    val floatAudioTensor = OnnxTensor.createTensor(env, floatAudioData, tensorShape(1, 512))
    inputs["wav"] = floatAudioTensor

    // Run inference
    val outputs = session.run(inputs)

    // Parse outputs
    val speechScore = outputs.use {
      @Suppress("UNCHECKED_CAST")
      (it[1].value as Array<FloatArray>)[0][0]
    }

    return speechScore
  }

  override fun close() {
    baseInputs.values.forEach {
      it.close()
    }
    session.close()
  }

  companion object {


    private const val SAMPLING_RATE = 8000L
    private const val WINDOW_LENGTH = 64 * (SAMPLING_RATE / 1000)
//    val windowLength: Long = 0.064 * samplingRate

  }

//  private fun createOrtSession(): OrtSession {
//    val so = OrtSession.SessionOptions()
//    so.use {
//      // Set to use 2 intraOp threads for CPU EP
//      so.setIntraOpNumThreads(2)
//
//      if (enableNNAPI)
//        so.addNnapi()
//
//      return env.createSession(modelBytes, so)
//    }
//  }
}