package com.olaroc.core.uikit.toast

import android.graphics.drawable.GradientDrawable
import android.os.Build
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.olaroc.core.R
import com.olaroc.core.uikit.extension.colorInt
import com.olaroc.core.uikit.extension.dp

internal class ToastStyle(private val darkMode: Boolean) {

    @ColorInt
    fun getBackgroundColor(): Int {
        @ColorRes val backgroundColor = if (darkMode)
            R.color.toast_background_dark else R.color.toast_background_light
        return backgroundColor.colorInt
    }

    fun getBackgroundDrawable() = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setColor(getBackgroundColor())
        cornerRadius = 16.dp.toFloat()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            setPadding(16.dp, 12.dp, 16.dp, 12.dp)
        }
    }


    @ColorInt
    fun getTextColor(): Int {
        @ColorRes val textColor =
            if (darkMode) R.color.toast_text_dark else R.color.toast_text_light
        return textColor.colorInt
    }
}