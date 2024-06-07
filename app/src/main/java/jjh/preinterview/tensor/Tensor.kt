package jjh.preinterview.tensor


import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtLoggingLevel
import ai.onnxruntime.OrtSession
import jjh.preinterview.tensor.CreateTensor.createLongTensor
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer


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
    baseInputs.values.forEach { it.close() }
    session.close()
  }

  // wav file start model
//  fun startModel(soundFileByteArray: ByteArray) {
//    val floatArray = TensorConvertor.byteArrayToFloatArray(soundFileByteArray)
//    val length = 512
//    var offset = 0
//    val mutableList = mutableListOf<Float>()
//    while (offset < floatArray.size) {
//
//      val floatBuffer =
//        TensorConvertor.floatArrayToFloatBuffer(offset, length, floatArray) ?: continue
//
//      val speechScore = run(floatBuffer)
//      offset += length / 2
//      mutableList.add(speechScore)
//    }
//  }

  fun startModel(floatArray: FloatArray): FloatArray {
    val speechScoreArray = FloatArray(floatArray.size)

    val length = 512
    var offset = 0
    var index = 0

    // 512 크기로 잘라서 모델에 run
    while (offset < floatArray.size) {

      val floatBuffer =
        TensorConvertor.floatArrayToFloatBuffer(offset, length, floatArray) ?: continue

      offset += length / 2

      val speechScore = run(floatBuffer)
      speechScoreArray[index++] = speechScore
    }

    return speechScoreArray
  }

  companion object {
    private const val SAMPLING_RATE = 8000L
    private const val WINDOW_LENGTH = 64 * (SAMPLING_RATE / 1000)
  }
}