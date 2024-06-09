package jjh.data.remote

import jjh.data.response.GetNicknameResponse
import jjh.data.response.SignInResponse
import jjh.data.util.Urls
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {

  @POST("/analyze")
  suspend fun test(
    @Body map: HashMap<String, Any>?,
  )

  // 회원가입
  @POST(Urls.POST_SIGN_UP)
  suspend fun signup(
    @Body map: HashMap<String, Any>,
  )

  // 로그인
  @POST(Urls.POST_SIGN_IN)
  suspend fun signIn(
    @Body map: HashMap<String, Any>,
  ): SignInResponse

  // 로그인
  @GET(Urls.GET_NICKNAME)
  suspend fun getNickname(
  ): GetNicknameResponse
}