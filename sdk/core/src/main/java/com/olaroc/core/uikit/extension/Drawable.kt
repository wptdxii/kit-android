package com.olaroc.core.extension

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.core.content.ContextCompat
import com.olaroc.core.uikit.extension.dp

fun View.roundedRectangleBackground(@ColorInt color: Int = Color.WHITE, radius: Int = 10.dp) {
  background = GradientDrawable().apply {
    setColor(color)
    cornerRadius = radius.toFloat()
  }
}

fun View.arrow(@ColorRes arrowColor: Int = android.R.color.black) =
  DrawerArrowDrawable(context).apply {
    color = ContextCompat.getColor(context, arrowColor)
    progress = 1.0F
  }