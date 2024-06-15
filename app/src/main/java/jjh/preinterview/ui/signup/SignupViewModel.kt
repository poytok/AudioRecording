package jjh.preinterview.ui.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.domain.SignupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
  private val signupRepository: SignupRepository,
) : ViewModel() {
  var state by mutableStateOf(SignupUiState())
    private set

  fun setUserId(userId: String?) {
    state = state.copy(userId = userId ?: "")
  }

  fun setNickname(nickname: String) {
    val maxLength = nickname.length.coerceAtMost(12)
    state = state.copy(nickname = nickname.substring(0, maxLength))
  }

  fun startSignup(userId: String) {
    viewModelScope.launch(Dispatchers.IO) {
      signupRepository(userId, state.nickname)
    }
  }
}