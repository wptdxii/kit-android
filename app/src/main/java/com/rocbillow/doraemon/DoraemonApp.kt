package com.rocbillow.doraemon

import androidx.lifecycle.ProcessLifecycleOwner
import com.rocbillow.base.generic.BaseApplication
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
    ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
  }
}
