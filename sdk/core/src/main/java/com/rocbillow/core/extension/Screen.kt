package com.rocbillow.core.extension

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.view.WindowManager
import com.rocbillow.core.assist.ContextProvider

val screenWidth: Int
    get() = getDisplayPoint().x

val screenHeight: Int
    get() = getDisplayPoint().y

private fun getDisplayPoint(): Point {
    val context = ContextProvider.context
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager?
    val point = Point()
    val display = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        context.display
    } else {
        @Suppress("DEPRECATION")
        windowManager?.defaultDisplay
    }
    display?.getRealSize(point)
    return point
}