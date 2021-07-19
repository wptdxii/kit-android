package com.olaroc.core.uikit.extension

import android.content.res.Resources

/**
 * dp to px
 */
val Number.dp: Int
  get() {
    val density = Resources.getSystem().displayMetrics.density
    return (toFloat() * density + 0.5F).toInt()
  }