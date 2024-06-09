package jjh.data.response

import jjh.domain.model.SignInModel

data class SignInResponse(
  val accessToken: String,
) {
  fun toModel(): SignInModel {
    return SignInModel(accessToken)
  }
}
