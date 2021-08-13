package com.olaroc.core.systembar

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import dev.chrisbanes.insetter.applyInsetter


fun Activity.applyNotFitsSystemWindows() {
    WindowCompat.setDecorFitsSystemWindows(window, false)
}

fun Activity.applyFullScreen() {
    WindowCompat.getInsetsController(window, window.decorView)
        ?.hide(WindowInsetsCompat.Type.systemBars())
}

fun Activity.applyLightTheme() {
    applySystemBarTheme(light = true)
}

fun Activity.applyDarkTheme() {
    applySystemBarTheme(light = false)
}

fun Activity.applySystemBarTheme(light: Boolean) {
    WindowCompat.getInsetsController(window, window.decorView)
        ?.let { insetsController ->
            insetsController.isAppearanceLightStatusBars = light
            insetsController.isAppearanceLightNavigationBars = light
        }
}

fun View.applyStatusBarInsetsToMargin() {
    applyInsetter {
        type(statusBars = true) {
            margin()
        }
    }
}

fun View.applyStatusBarInsetsToPadding() {
    applyInsetter {
        type(statusBars = true) {
            padding()
        }
    }
}

fun View.applyNavigationBarInsetsToPadding() {
    applyInsetter {
        type(navigationBars = true) {
            padding()
        }
    }
}

/**
 * [How to detect full screen gesture mode in android 10](https://stackoverflow.com/questions/56689210/how-to-detect-full-screen-gesture-mode-in-android-10/56792591#56792591)
 */
fun Context.isGestureNavigationEnable(): Boolean {
    val resourcesId = resources.getIdentifier(
        "config_navBarInteractionMode", "integer", "android"
    )
    if (resourcesId > 0) {
        return resources.getInteger(resourcesId) == 2
    }
    return false
}
