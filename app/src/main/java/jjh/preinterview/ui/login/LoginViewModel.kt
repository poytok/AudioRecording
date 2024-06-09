package jjh.preinterview.ui.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jjh.data.util.TokenManager
import jjh.domain.SignIpRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
  private val signIpRepository: SignIpRepository,
  private val tokenManager: TokenManager,
) : ViewModel() {

  suspend fun startLogin(userId: String): Boolean {
    val token = signIpRepository(userId).accessToken
    tokenManager.saveAccessToken(token)
    return token.isNotEmpty()
  }


}