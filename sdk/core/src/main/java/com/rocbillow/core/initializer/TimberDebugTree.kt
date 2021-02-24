package com.rocbillow.core.initializer

import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.rocbillow.core.BuildConfig
import timber.log.Timber

/**
 * @author rocbillow
 * @date 2020-12-23
 */
class TimberDebugTree : Timber.DebugTree() {

  companion object {
    private const val TAG = "Timber"
    private const val METHOD_COUNT = 1
    private const val METHOD_OFFSET = 4
  }

  init {
    Logger.addLogAdapter(createLogAdapter())
  }

  private fun createLogAdapter(): LogAdapter {
    return object : AndroidLogAdapter(createPrettyFormatStrategy()) {
      override fun isLoggable(priority: Int, tag: String?) = BuildConfig.DEBUG
    }
  }

  private fun createPrettyFormatStrategy(): PrettyFormatStrategy {
    return PrettyFormatStrategy.newBuilder()
      .methodCount(METHOD_COUNT)
      .methodOffset(METHOD_OFFSET)
      .tag(TAG)
      .build()
  }

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    if (isJson(message)) {
      Logger.t(tag)
      Logger.json(message)
      return
    }
    Logger.log(priority, tag, message, t)
  }

  private fun isJson(message: String): Boolean = with(message) {
    startsWith("{") && endsWith("}")
  }
}
