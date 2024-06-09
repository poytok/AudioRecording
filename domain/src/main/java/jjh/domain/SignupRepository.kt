package jjh.domain

interface SignupRepository {
  suspend operator fun invoke(userId: String, username: String)
}