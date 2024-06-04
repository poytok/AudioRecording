package jjh.data.remote

import com.launchdarkly.eventsource.MessageEvent
import com.launchdarkly.eventsource.background.BackgroundEventHandler
import com.orhanobut.logger.Logger

class SseEventHandler : BackgroundEventHandler {

  // SSE 연결 성공시 처리 로직 작성
  override fun onOpen() {
    Logger.e("SseEventHandler onOpen")
  }

  // SSE 연결 종료시 처리 로직 작성
  override fun onClosed() {
    Logger.e("SseEventHandler onClosed")
  }


  /*
  * SSE 이벤트 도착시 처리 로직 작성
  * event: String = 이벤트가 속한 채널 또는 토픽 이름
  * messageEvent.lastEventId: String = 도착한 이벤트 ID
  * messageEvent.data: String = 도착한 이벤트 데이터
  * */
  override fun onMessage(event: String?, messageEvent: MessageEvent?) {
    Logger.e(
      """
      SseEventHandler
      onMessage
      event         : $event
      messageEvent  : $messageEvent
    """.trimIndent()
    )
  }

  override fun onComment(comment: String?) {
    Logger.e(
      """
      onComment
      comment         : $comment
    """.trimIndent()
    )
  }

  /*
  * SSE 연결 전 또는 후 오류 발생시 처리 로직 작성
  * 서버가 2XX 이외의 오류 응답시 com.launchdarkly.eventsource.StreamHttpErrorException: Server returned HTTP error 401 예외가 발생
  * 클라이언트에서 서버의 연결 유지 시간보다 짧게 설정시 error=com.launchdarkly.eventsource.StreamIOException: java.net.SocketTimeoutException: timeout 예외가 발생
  * 서버가 연결 유지 시간 초과로 종료시 error=com.launchdarkly.eventsource.StreamClosedByServerException: Stream closed by server 예외가 발생
  * */
  override fun onError(t: Throwable?) {
    Logger.e(
      """
      SseEventHandler
      onError
      t         : $t
    """.trimIndent()
    )
    t?.printStackTrace()
  }
}