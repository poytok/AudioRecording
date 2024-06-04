package jjh.study.audiorecording.tensor

import java.io.ByteArrayInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

object TensorConvertor {

  fun shortArrayToFloatArray(shortArray: ShortArray): FloatArray {
    return shortArray.map { it.toFloat() / 32768.0f }.toFloatArray()
  }

  fun byteArrayToFloatArray(soundFileByteArray: ByteArray): FloatArray {
    val floatArray = FloatArray(soundFileByteArray.size / 4)
    val `is` = ByteArrayInputStream(soundFileByteArray)
//    `is`.skip(58)
    val byteArray = ByteArray(4)
    var index = 0

    `is`.use {
      while (`is`.read(byteArray) != -1) {
        val readValue = ByteBuffer.wrap(byteArray).order(ByteOrder.LITTLE_ENDIAN).getFloat()
        floatArray[index++] = readValue
      }
    }
    return floatArray
  }

  fun floatArrayToFloatBuffer(
    offset: Int,
    length: Int,
    floatArray: FloatArray,
  ): FloatBuffer? {
    return if (offset + length > floatArray.size) {
      FloatBuffer.wrap(FloatArray(length) { floatArray.getOrNull(offset + it) ?: 0f }, 0, 512)
    } else {
      FloatBuffer.wrap(floatArray, offset, length)
    }
  }
}