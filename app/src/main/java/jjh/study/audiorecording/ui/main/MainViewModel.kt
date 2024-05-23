package jjh.study.audiorecording.ui.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.study.audiorecording.remote.RetrofitService
import jjh.study.audiorecording.ui.select_question.QuestionModel
import jjh.study.audiorecording.ui.select_question.questionList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
  private val retrofitService: RetrofitService,
) : ViewModel() {
  var state by mutableStateOf<List<QuestionModel>>(listOf())
    private set

  init {
    state = questionList
    viewModelScope.launch {
      val map: HashMap<String, Any> = hashMapOf(
        "pcm" to arrayOf<String>(),
        "createDate" to "2024-05-23T07:17:45.981Z"
      )
      retrofitService.test(map)
    }
  }

  fun onCheckedChange(index: Int, checked: Boolean) {
    val questionList = state.toMutableList()
    questionList[index] = state[index].copy(checked = checked)
    state = questionList
  }
}