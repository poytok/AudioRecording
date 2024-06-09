package jjh.preinterview.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.domain.GetNicknameRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val getNicknameRepositoryImpl: GetNicknameRepository,
) : ViewModel() {
  private val _homeUiState: MutableState<HomeUiState> = mutableStateOf(HomeUiState.init())
  val homeUiState: State<HomeUiState> = _homeUiState

  init {
    getUsername()
  }
  fun setQuestion(question: Question) {
    _homeUiState.value = homeUiState.value.copy(
      selectedQuestion = question
    )
  }

  fun getUsername() {
    viewModelScope.launch {
      getNicknameRepositoryImpl()
    }
  }
}