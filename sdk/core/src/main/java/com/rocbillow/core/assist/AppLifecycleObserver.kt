package com.rocbillow.core.assist

import com.rocbillow.core.base.BaseAppLifecycleObserver

/**
 * @author rocbillow
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