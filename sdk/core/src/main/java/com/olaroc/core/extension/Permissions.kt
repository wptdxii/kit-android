package com.olaroc.core.extension

import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import com.olaroc.core.assist.ContextProvider


fun areNotificationsEnabled() =
  NotificationManagerCompat.from(ContextProvider.context).areNotificationsEnabled()

fun canDrawOverlays() = Settings.canDrawOverlays(ContextProvider.context)