package com.rocbillow.base.extension

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.ColorInt

fun View.roundedRectangleBackground(@ColorInt color: Int = Color.WHITE, radius: Int = 10.dp) {
  background = GradientDrawable().apply {
    setColor(color)
    cornerRadius = radius.toFloat()
  }
}