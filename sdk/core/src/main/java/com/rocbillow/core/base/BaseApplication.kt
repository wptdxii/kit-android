package com.rocbillow.core.base

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.rocbillow.core.assist.ActivityLifecycleCallbacksImpl
import com.rocbillow.core.assist.AppLifecycleObserver

/**
 * @author rocbillow
 * @date 2020-09-04
 */
abstract class BaseApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImpl())
    ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
  }
}
