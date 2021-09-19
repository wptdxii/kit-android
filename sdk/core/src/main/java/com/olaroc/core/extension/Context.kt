package com.olaroc.core.extension

import android.content.Context
import android.content.pm.ApplicationInfo

val Context.isDebug: Boolean
    get() = (applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0