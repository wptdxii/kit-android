package com.rocbillow.core.assist

import com.rocbillow.core.base.BaseAppLifecycleObserver

private typealias OnForegroundCallback = () -> Unit
private typealias OnBackgroundCallback = () -> Unit

class AppLifecycleObserverBuilder : BaseAppLifecycleObserver() {

  private var onForegroundCallback: OnForegroundCallback? = null

  private var onBackgroundCallback: OnBackgroundCallback? = null

  override fun onForeground() = onForegroundCallback?.invoke() ?: Unit

  override fun onBackground() = onBackgroundCallback?.invoke() ?: Unit

  fun onForeground(onForegroundCallback: OnForegroundCallback) {
    this.onForegroundCallback = onForegroundCallback
  }

  fun onBackground(onBackgroundCallback: OnBackgroundCallback) {
    this.onBackgroundCallback = onBackgroundCallback
  }
}

fun createAppLifecycleObserver(observe: AppLifecycleObserverBuilder.() -> Unit) =
  AppLifecycleObserverBuilder().also(observe)
