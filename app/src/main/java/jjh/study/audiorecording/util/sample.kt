//https://github.com/microsoft/onnxruntime-inference-examples/blob/main/mobile/examples/whisper/local/android/app/src/main/java/ai/onnxruntime/example/whisperLocal/TensorUtils.kt
//
//import ai.onnxruntime.OnnxTensor
//import ai.onnxruntime.OrtEnvironment
//import java.nio.FloatBuffer
//import java.nio.IntBuffer
//
//import ai.onnxruntime.OnnxTensor
//import ai.onnxruntime.OrtEnvironment
//import ai.onnxruntime.OrtSession
//import ai.onnxruntime.extensions.OrtxPackage
//
//internal fun createIntTensor(env: OrtEnvironment, data: IntArray, shape: LongArray): OnnxTensor {
//    return OnnxTensor.createTensor(env, IntBuffer.wrap(data), shape)
//}
//
//internal fun createFloatTensor(
//    env: OrtEnvironment,
//    data: FloatArray,
//    shape: LongArray
//): OnnxTensor {
//    return OnnxTensor.createTensor(env, FloatBuffer.wrap(data), shape)
//}
//
//internal fun tensorShape(vararg dims: Long) = longArrayOf(*dims)
//
//
//
//class VoiceActivityDetection(modelBytes: ByteArray) : AutoCloseable {
//    private val session: OrtSession
//    private val baseInputs: Map<String, OnnxTensor>
//
//    init {
//        // Load ONNX model
//        val env = OrtEnvironment.getEnvironment()
//        val sessionOptions = OrtSession.SessionOptions()
//        sessionOptions.registerCustomOpLibrary(OrtxPackage.getLibraryPath())
//        session = env.createSession(modelBytes, sessionOptions)
//
//        val samplingRate: Long = 8000
//        val windowLength: Long = 0.064 * samplingRate
//
//        baseInputs = mapOf(
//            "length" to createIntTensor(env, intArrayOf(windowLength), tensorShape(1))
//        )
//    }
//
//
//    fun run(floatAudioData: FloatBuffer): Float {
//
//        // Prepare input map
//        val inputs = mutableMapOf<String, OnnxTensvor>()
//        baseInputs.toMap(inputs)
//
//        val floatAudioTensor = OnnxTensor.createTensor(
//            env,
//            floatAudioData,
//            tensorShape(1, floatAudioData.size.toLong()) // floatAudioData.size == windowLength
//        )
//        inputs["wav"] = floatAudioTensor
//
//        // Run inference
//        val outputs = session.run(inputs)
//
//        // Parse outputs
//        val speechScore = outputs.use {
//            @Suppress("UNCHECKED_CAST")
//            (outputs[0].value as Array<Array<Float>>)[0]
//        }
//
//        speechScore
//    }
//
//    override fun close() {
//        baseInputs.values.forEach {
//            it.close()
//        }
//        session.close()
//    }
//}