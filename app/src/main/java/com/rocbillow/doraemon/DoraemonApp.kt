package com.rocbillow.doraemon

import com.rocbillow.base.generic.GenericApplication
import dagger.hilt.android.HiltAndroidApp

/**
 * @author rocbillow
 * @date 2020-09-04
 */
@HiltAndroidApp
class DoraemonApp : GenericApplication() {
  override fun onCreate() {
    super.onCreate()
    registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImpl())
  }
}
