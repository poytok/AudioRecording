package jjh.preinterview.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
  private val _homeUiState: MutableState<HomeUiState> = mutableStateOf(HomeUiState.init())
  val homeUiState: State<HomeUiState> = _homeUiState


  fun setQuestion(question: Question) {
    _homeUiState.value = homeUiState.value.copy(
      selectedQuestion = question
    )
  }
}