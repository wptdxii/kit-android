package com.olaroc.core.uikit.toast

import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.olaroc.core.extension.areNotificationsEnabled
import com.olaroc.core.extension.canDrawOverlays

interface ToastStrategyFactory {
  fun create(): ToastStrategy
}

internal class ToastStrategyFactoryImpl(private val builder: ToastManager.Builder) :
  ToastStrategyFactory {

  @Suppress("DEPRECATION")
  override fun create(): ToastStrategy {
    if (areNotificationsEnabled() and isCustomSystemToastEnabled(builder)) {
      return SystemToastStrategy(builder)
    }

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
      return WindowManagerToastStrategy(builder, WindowManager.LayoutParams.TYPE_TOAST)
    } else if (canDrawOverlays()) {
      return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        WindowManagerToastStrategy(builder, WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY)
      } else {
        WindowManagerToastStrategy(builder, WindowManager.LayoutParams.TYPE_PHONE)
      }
    }
    return ActivityToastStrategy(builder)
  }

  private fun isCustomSystemToastEnabled(builder: ToastManager.Builder): Boolean {
    val view: View? = builder.view
    val gravity = builder.gravity
    val isNotCustomToast = (view == null) and (gravity == Gravity.NO_GRAVITY)
    return isNotCustomToast or (Build.VERSION.SDK_INT < Build.VERSION_CODES.R)
  }
}