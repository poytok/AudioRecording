package jjh.preinterview.tensor

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import java.nio.FloatBuffer
import java.nio.IntBuffer
import java.nio.LongBuffer

object CreateTensor {

  fun createIntTensor(env: OrtEnvironment, data: IntArray, shape: LongArray): OnnxTensor {
    return OnnxTensor.createTensor(env, IntBuffer.wrap(data), shape)
  }

  fun createLongTensor(env: OrtEnvironment, data: LongArray, shape: LongArray): OnnxTensor {
    return OnnxTensor.createTensor(env, LongBuffer.wrap(data), shape)
  }

  fun createFloatTensor(
    env: OrtEnvironment,
    data: FloatArray,
    shape: LongArray,
  ): OnnxTensor {
    return OnnxTensor.createTensor(env, FloatBuffer.wrap(data), shape)
  }
}