package jjh.data.remote

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jjh.data.util.NetworkLogger
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
  private const val CONNECT_TIMEOUT = 15L
  private const val WRITE_TIMEOUT = 15L
  private const val READ_TIMEOUT = 15L

  @Provides
  fun okHttp3(): OkHttpClient {
    return OkHttpClient.Builder()
      .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
      .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
      .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
      .addInterceptor(HttpLoggingInterceptor(NetworkLogger()).apply {
        setLevel(HttpLoggingInterceptor.Level.BODY)
      })
      .build()
  }

  @Provides
  fun retrofit(okHttpClient: OkHttpClient): Retrofit {
    val baseUrl = "https://2648-112-155-175-197.ngrok-free.app/"

    return Retrofit.Builder()
      .baseUrl(baseUrl)
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttpClient)
      .build()
  }

  @Provides
  fun retrofitService(retrofit: Retrofit): RetrofitService {
    return retrofit.create(RetrofitService::class.java)
  }
}