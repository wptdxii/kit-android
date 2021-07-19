package com.olaroc.core.assist

import com.olaroc.core.base.BaseAppLifecycleObserver

/**
 * @author olaroc
 * @date 2020-12-24
 */
class AppLifecycleObserver : BaseAppLifecycleObserver() {

  override fun onForeground() {
    AppManager.isForeground = true
  }

  override fun onBackground() {
    AppManager.isForeground = false
  }
}