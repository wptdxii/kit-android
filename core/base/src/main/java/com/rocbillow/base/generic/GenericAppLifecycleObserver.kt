package com.rocbillow.base.generic

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * App lifecycle observer for the whole application process.
 *
 * @author rocbillow
 * @date 2020-12-24
 */
abstract class GenericAppLifecycleObserver : DefaultLifecycleObserver {

  override fun onStart(owner: LifecycleOwner) {
    super.onStart(owner)
    onForeground()
  }

  override fun onStop(owner: LifecycleOwner) {
    super.onStop(owner)
    onBackground()
  }

  abstract fun onForeground()

  abstract fun onBackground()
}