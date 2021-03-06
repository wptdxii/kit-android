package com.rocbillow.core.assist

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * @author rocbillow
 * @date 2020-10-26
 */
class ActivityLifecycleCallbacksImpl : Application.ActivityLifecycleCallbacks {

  override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    ActivityManager.add(activity)
  }

  override fun onActivityStarted(activity: Activity) = Unit

  override fun onActivityResumed(activity: Activity) = Unit

  override fun onActivityPaused(activity: Activity) = Unit

  override fun onActivityStopped(activity: Activity) = Unit

  override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

  override fun onActivityDestroyed(activity: Activity) {
    ActivityManager.remove(activity)
  }
}
