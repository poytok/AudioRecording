package jjh.data.repository

import jjh.data.remote.RetrofitService
import jjh.domain.SignupRepository
import javax.inject.Inject

class SignupRepositoryImpl @Inject constructor(
  private val retrofitService: RetrofitService,
) : SignupRepository {

  override suspend operator fun invoke(userId: String, username: String) {
    retrofitService.signup(
      hashMapOf(
        "userId" to userId,
        "username" to username
      )
    )
  }
}