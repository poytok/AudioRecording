package jjh.study.audiorecording.tensor


import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtLoggingLevel
import ai.onnxruntime.OrtSession
import com.orhanobut.logger.Logger
import java.nio.ByteBuffer
import java.nio.ByteOrder
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


private fun getShortArray(byteArray: ByteArray): MutableList<FloatArray> {
  // 한 번에 읽을 바이트 수
  val bufferSize = 1024

  val floatArrayList = mutableListOf<FloatArray>()

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

class VoiceActivityDetection(
  modelBytes: ByteArray,
) : AutoCloseable {
  private val session: OrtSession
  private val baseInputs: Map<String, OnnxTensor>

  // Load ONNX model
  private val env: OrtEnvironment = OrtEnvironment.getEnvironment(OrtLoggingLevel.ORT_LOGGING_LEVEL_FATAL)

  init {
    val sessionOptions = OrtSession.SessionOptions()
//    sessionOptions.registerCustomOpLibrary(OrtxPackage.getLibraryPath())
    session = env.createSession(modelBytes, sessionOptions) // createOrtSession()

    baseInputs = mapOf("length" to createLongTensor(env, longArrayOf(WINDOW_LENGTH), tensorShape(1)))
  }


  private fun run(floatAudioData: FloatBuffer): Float {
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

  fun startModel(soundFileByteArray: ByteArray) {
    val floatArray = TensorConvertor.byteArrayToFloatArray(soundFileByteArray)
    val length = 512
    var offset = 0
    while (offset < floatArray.size) {

      val floatBuffer =
        TensorConvertor.floatArrayToFloatBuffer(offset, length, floatArray) ?: continue

      val speechScore = run(floatBuffer)
      Logger.e(
        """
        speechScore >> $speechScore
        $offset / ${floatArray.size}
      """.trimIndent()
      )
      offset += length / 2
    }
  }

  companion object {
    private const val SAMPLING_RATE = 8000L
    private const val WINDOW_LENGTH = 64 * (SAMPLING_RATE / 1000)
  }
}