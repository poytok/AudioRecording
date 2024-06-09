package jjh.data.repository

import jjh.data.remote.RetrofitService
import jjh.domain.GetNicknameRepository
import jjh.domain.model.GetNicknameModel
import javax.inject.Inject

class GetNicknameRepositoryImpl @Inject constructor(
  private val retrofitService: RetrofitService,
) : GetNicknameRepository {


  override suspend operator fun invoke(): GetNicknameModel {
    return retrofitService.getNickname().toModel()
  }
}