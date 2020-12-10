package com.rocbillow.doraemon

import com.rocbillow.base.base.BaseApplication
import dagger.hilt.android.HiltAndroidApp

/**
 * @author rocbillow
 * @date 2020-09-04
 */
@HiltAndroidApp
class DoraemonApp : BaseApplication() {
  override fun onCreate() {
    super.onCreate()
    registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImpl())
  }
}
