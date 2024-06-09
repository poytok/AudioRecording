package jjh.data.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jjh.data.util.NetworkLogger
import jjh.data.util.TokenManager
import jjh.data.util.Urls.BASE_URL
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  private const val CONNECT_TIMEOUT = 60L
  private const val WRITE_TIMEOUT = 60L
  private const val READ_TIMEOUT = 60L

  @Provides
  fun okHttp3(tokenManager: TokenManager): OkHttpClient {
    return OkHttpClient.Builder()
      .apply {
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        addInterceptor(HttpLoggingInterceptor(NetworkLogger()).apply {
          setLevel(HttpLoggingInterceptor.Level.BODY)
        })

        addInterceptor { chain ->
          val request = chain.request()
          val requestBuilder = request.newBuilder()

          val token: String = runBlocking { tokenManager.getAccessToken().first() ?: "" }
          requestBuilder.header("AUTHORIZATION", "Bearer $token").build()

          val response = chain.proceed(requestBuilder.build())
          response
        }
      }
      .build()
  }

  @Provides
  fun retrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttpClient)
      .build()
  }

  @Provides
  fun retrofitService(retrofit: Retrofit): RetrofitService {
    return retrofit.create(RetrofitService::class.java)
  }
}