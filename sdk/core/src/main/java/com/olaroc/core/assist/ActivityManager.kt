package com.olaroc.core.assist

import android.app.Activity

object ActivityManager {

  private val activities = mutableListOf<Activity>()

  fun add(activity: Activity) {
    activities.add(activity)
  }

  fun remove(activity: Activity) {
    activities.remove(activity)
  }

  fun finishAll() {
    for (activity in activities) {
      if (!activity.isFinishing) {
        activity.finish()
      }
    }
  }

  fun currentActivity(): Activity? {
    if (activities.isNotEmpty()) {
      return activities.last()
    }
    return null
  }
}