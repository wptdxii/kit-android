package com.rocbillow.core.extension

import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import com.rocbillow.core.assist.ContextProvider


fun areNotificationsEnabled() =
  NotificationManagerCompat.from(ContextProvider.context).areNotificationsEnabled()

fun canDrawOverlays() = Settings.canDrawOverlays(ContextProvider.context)