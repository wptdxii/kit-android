package com.rocbillow.core.extension

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed


val globalMainHandler: Handler by lazy {
  Handler(Looper.getMainLooper())
}

fun runOnUiThread(action: () -> Unit) {
  if (isMainThread()) {
    action()
    return
  }
  globalMainHandler.post(action)
}

fun isMainThread() = Looper.myLooper() == Looper.getMainLooper()

fun runOnUiThread(delayMillis: Long, action: () -> Unit) {
  globalMainHandler.postDelayed(delayInMillis = delayMillis, action = action)
}