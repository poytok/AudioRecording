package jjh.study.audiorecording.util

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtLoggingLevel
import ai.onnxruntime.OrtSession
import android.content.Context
import android.graphics.Bitmap
import android.os.SystemClock
import jjh.study.audiorecording.R
import java.nio.FloatBuffer
import java.util.Collections

class FROnnxMobileNet2(private val context: Context) {

  //안드로이드 가속화
  private var enableNNAPI: Boolean = false

  //오닉스 런타임 (ONNX)
  private var ortEnv: OrtEnvironment? = null
  private var ortSession: OrtSession? = null

  companion object {
    const val IMAGE_MEAN: Float = .0f
    const val IMAGE_STD: Float = 255f
    const val DIM_BATCH_SIZE = 1
    const val DIM_PIXEL_SIZE = 3
    const val IMAGE_SIZE_X = 128
    const val IMAGE_SIZE_Y = 128
  }


  init {
    ortEnv = OrtEnvironment.getEnvironment(OrtLoggingLevel.ORT_LOGGING_LEVEL_FATAL)
    ortSession = CreateOrtSession()
  }

  private fun CreateOrtSession(): OrtSession? {
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
    return res.openRawResource(R.raw.mobileface).readBytes()
  }



  private fun preprocess2(bitmap: Bitmap): FloatBuffer {
    val imgData = FloatBuffer.allocate(
      DIM_BATCH_SIZE
      * IMAGE_SIZE_X
      * IMAGE_SIZE_Y
      * DIM_PIXEL_SIZE)
    imgData.rewind()

    val bmpData = IntArray(IMAGE_SIZE_X * IMAGE_SIZE_Y)
    bitmap.getPixels(bmpData, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)

    var idx: Int = 0
    for (i in 0..IMAGE_SIZE_X - 1) {
      for (j in 0..IMAGE_SIZE_Y - 1) {
        val pixelValue = bmpData[idx++]
        imgData.put(((pixelValue shr 16 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
        imgData.put(((pixelValue shr 8 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
        imgData.put(((pixelValue and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
      }
    }

    imgData.rewind()
    return imgData
  }

  fun analyze(bitmap: Bitmap) : FloatArray {
    val resizeBitmap = Bitmap.createScaledBitmap(bitmap, 128, 128, false)
    val imgData = preprocess2(resizeBitmap) //128x128 => 49152

    val inputName = ortSession?.inputNames?.iterator()?.next()

    val shape = longArrayOf(1, 3, 128, 128)
//            val shape = longArrayOf(3, 224, 224)
    val ortEnv = OrtEnvironment.getEnvironment()
    ortEnv.use {
      // Create input tensor
      val input_tensor = OnnxTensor.createTensor(ortEnv, imgData, shape)
      val startTime = SystemClock.uptimeMillis()
      input_tensor.use {
        // Run the inference and get the output tensor
        val output = ortSession?.run(Collections.singletonMap(inputName, input_tensor))
//                    val output = ortSession?.run(Collections.singletonMap("input", input_tensor))
        output.use {

          //reshape(-1)
          val output0 = (output?.get(0)?.value) as Array<Array<Array<FloatArray>>>
          val output1 = output0[0].flatten()
          output1[0].forEach { }
          val output2 = FloatArray(output1.size)
          output1.forEachIndexed { index, floats ->
            floats.forEach {
              output2[index] = it
            }
          }

          output.close()

          return output2
        }
      }
    }
  }

}