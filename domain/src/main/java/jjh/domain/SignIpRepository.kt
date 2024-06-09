package jjh.domain

import jjh.domain.model.SignInModel

interface SignIpRepository {
  suspend operator fun invoke(userId: String): SignInModel
}