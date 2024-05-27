package jjh.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {

  @POST("/analyze")
  suspend fun test(
    @Body map: HashMap<String, Any>?,
  ): Unit
}