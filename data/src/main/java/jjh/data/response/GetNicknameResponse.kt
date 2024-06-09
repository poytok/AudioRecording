package jjh.data.response

import jjh.domain.model.GetNicknameModel

data class GetNicknameResponse(
  val username: String,
) {
  fun toModel(): GetNicknameModel {
    return GetNicknameModel(username)
  }
}
