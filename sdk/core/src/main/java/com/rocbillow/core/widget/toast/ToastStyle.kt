package com.rocbillow.core.widget.toast

import android.graphics.drawable.GradientDrawable
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.rocbillow.core.R
import com.rocbillow.core.assist.ContextProvider
import com.rocbillow.core.extension.dp

internal class ToastStyle(private val darkMode: Boolean) {

  @ColorInt
  fun getBackgroundColor(): Int {
    val backgroundColor = if (darkMode)
      R.color.toast_background_dark else R.color.toast_background_light
    return getColor(backgroundColor)
  }

  fun getBackgroundDrawable() = GradientDrawable().apply {
    shape = GradientDrawable.RECTANGLE
    setColor(getBackgroundColor())
    cornerRadius = 16.dp.toFloat()
    setPadding(16.dp, 12.dp, 16.dp, 12.dp)
  }


  @ColorInt
  fun getTextColor(): Int {
    val textColor = if (darkMode) R.color.toast_text_dark else R.color.toast_text_light
    return getColor(textColor)
  }

  @ColorInt
  private fun getColor(@ColorRes color: Int) =
    ContextCompat.getColor(ContextProvider.context, color)
}