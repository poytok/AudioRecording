package jjh.study.audiorecording.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.data.remote.RetrofitService
import jjh.study.audiorecording.record.Record
import jjh.study.audiorecording.tensor.VoiceActivityDetection
import jjh.study.audiorecording.ui.select_question.QuestionModel
import jjh.study.audiorecording.ui.select_question.questionList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val retrofitService: RetrofitService,
  private val model : VoiceActivityDetection
) : ViewModel() {
  var state by mutableStateOf<List<QuestionModel>>(listOf())
    private set

  val record: Record by lazy { Record(model) }

  init {
    state = questionList
//    viewModelScope.launch {
//      val map: HashMap<String, Any> = hashMapOf(
//        "pcm" to arrayOf<String>(),
//        "createDate" to "2024-05-23T07:17:45.981Z"
//      )
//      retrofitService.test(map)
//    }
  }

  fun onCheckedChange(index: Int, checked: Boolean) {
    val questionList = state.toMutableList()
    questionList[index] = state[index].copy(checked = checked)
    state = questionList
  }

  fun startRecording() {
    viewModelScope.launch {
      record.startRecording()
    }
  }

  fun stopRecording() {
    record.stopRecording()
  }
}