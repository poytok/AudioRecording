package jjh.domain

import jjh.domain.model.GetNicknameModel

interface GetNicknameRepository {
  suspend operator fun invoke(): GetNicknameModel
}