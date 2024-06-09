package jjh.data.remote

import com.launchdarkly.eventsource.ConnectStrategy
import com.launchdarkly.eventsource.EventSource
import com.launchdarkly.eventsource.background.BackgroundEventSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jjh.data.util.Urls.BASE_URL
import java.net.URI
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SSE {

  @Provides
  @Singleton
  fun provideBackgroundEventSource(): BackgroundEventSource {
    val sseEventHandler = SseEventHandler()
    val baseUrl = BASE_URL
    val path = "/analyze/sse"

    val eventSourceBuilder = EventSource.Builder(
      ConnectStrategy
        .http(URI.create(baseUrl + path))
        .connectTimeout(3, TimeUnit.SECONDS)
        // 최대 연결 유지 시간을 설정, 서버에 설정된 최대 연결 유지 시간보다 길게 설정
        .readTimeout(600, TimeUnit.SECONDS)
    )

    return BackgroundEventSource
      .Builder(sseEventHandler, eventSourceBuilder)
      .build()
  }
}