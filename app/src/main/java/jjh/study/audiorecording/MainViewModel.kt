package jjh.study.audiorecording

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import jjh.study.audiorecording.ui.select_question.QuestionModel
import jjh.study.audiorecording.ui.select_question.questionList

class MainViewModel : ViewModel() {
  var state by mutableStateOf<List<QuestionModel>>(listOf())
    private set

  init {
    state = questionList
  }

  fun onCheckedChange(index: Int, checked: Boolean) {
    val questionList = state.toMutableList()
    questionList[index] = state[index].copy(checked = checked)
    state = questionList
  }
}