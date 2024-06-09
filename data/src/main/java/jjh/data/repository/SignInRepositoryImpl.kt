package jjh.data.repository

import jjh.data.remote.RetrofitService
import jjh.domain.SignIpRepository
import jjh.domain.model.SignInModel
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
  private val retrofitService: RetrofitService,
) : SignIpRepository {


  override suspend operator fun invoke(userId: String): SignInModel {
    return retrofitService.signIn(
      hashMapOf(
        "userId" to userId,
      )
    ).toModel()
  }
}