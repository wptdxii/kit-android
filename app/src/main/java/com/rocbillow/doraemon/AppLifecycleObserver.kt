package com.rocbillow.doraemon

import com.rocbillow.base.generic.GenericAppLifecycleObserver
import timber.log.Timber

/**
 * @author rocbillow
 * @date 2020-12-24
 */
class AppLifecycleObserver : GenericAppLifecycleObserver() {

  override fun onForeground() {
    Timber.d("onForeground")
  }

  override fun onBackground() {
    Timber.d("onBackground")
  }
}