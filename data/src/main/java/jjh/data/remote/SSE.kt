package jjh.data.remote

import com.launchdarkly.eventsource.ConnectStrategy
import com.launchdarkly.eventsource.EventSource
import com.launchdarkly.eventsource.background.BackgroundEventSource
import java.net.URI
import java.util.concurrent.TimeUnit

object SSE {

  fun test() {
    val sseEventHandler = SseEventHandler()
    val baseUrl = "https://d1c4-112-155-175-197.ngrok-free.app"
    val path = "/analyze/sse"

    val eventSourceBuilder = EventSource.Builder(
      ConnectStrategy
        .http(URI.create(baseUrl + path))
        .connectTimeout(3, TimeUnit.SECONDS)
        // 최대 연결 유지 시간을 설정, 서버에 설정된 최대 연결 유지 시간보다 길게 설정
        .readTimeout(600, TimeUnit.SECONDS)
    )

    BackgroundEventSource
      .Builder(sseEventHandler, eventSourceBuilder)
      .build()
      .start()
  }
}