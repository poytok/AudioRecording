package jjh.study.audiorecording.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.launchdarkly.eventsource.background.BackgroundEventSource
import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.data.remote.RetrofitService
import jjh.study.audiorecording.record.Record
import jjh.study.audiorecording.tensor.VoiceActivityDetection
import jjh.study.audiorecording.ui.select_question.QuestionModel
import jjh.study.audiorecording.ui.select_question.questionList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val retrofitService: RetrofitService,
  private val model: VoiceActivityDetection,
  private val backgroundEventSource: BackgroundEventSource,
) : ViewModel() {
  var state by mutableStateOf<List<QuestionModel>>(listOf())
    private set

  private val record: Record by lazy { Record(model) }

  init {
    backgroundEventSource.start()
    state = questionList
  }

  fun onCheckedChange(index: Int, checked: Boolean) {
    val questionList = state.toMutableList()
    questionList[index] = state[index].copy(checked = checked)
    state = questionList
  }

  // 파일로 실행
//  fun startModel(soundFileByteArray: ByteArray) {
//    viewModelScope.launch {
//      model.startModel(soundFileByteArray)
//    }
//  }

  // 녹음으로 실행
  fun startRecording() {
    viewModelScope.launch(Dispatchers.IO) {
      record.startRecording()
    }
  }

  fun stopRecording() {
    record.stopRecording()

    viewModelScope.launch(Dispatchers.IO) {
      val map: HashMap<String, Any> = hashMapOf(
        "pcm" to record.data,
        "createDate" to "2024-05-23T07:17:45.981Z"
      )

      try {
        retrofitService.test(map)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
}