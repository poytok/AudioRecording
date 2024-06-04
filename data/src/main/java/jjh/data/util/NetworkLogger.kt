package jjh.data.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.orhanobut.logger.Logger
import okhttp3.logging.HttpLoggingInterceptor

@Suppress("DEPRECATION")
class NetworkLogger : HttpLoggingInterceptor.Logger {
  override fun log(message: String) {
    if (message.startsWith("{") || message.startsWith("[")) {
      try {

        val prettyPrintJson = GsonBuilder().setPrettyPrinting()
          .create().toJson(JsonParser().parse(message)).trim()
        Logger.d(prettyPrintJson)
      } catch (m: JsonSyntaxException) {
        Logger.e(message)
      }
    } else {
      Logger.i(message)
      return
    }
  }
}