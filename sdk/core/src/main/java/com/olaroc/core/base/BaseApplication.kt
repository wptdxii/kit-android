package com.olaroc.core.base

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.olaroc.core.assist.ActivityLifecycleCallbacksImpl
import com.olaroc.core.assist.AppLifecycleObserver

/**
 * @author olaroc
 * @date 2020-09-04
 */
abstract class BaseApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    registerActivityLifecycleCallbacks(ActivityLifecycleCallbacksImpl())
    ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
  }
}
