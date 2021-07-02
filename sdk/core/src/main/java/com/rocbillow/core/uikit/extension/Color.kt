package com.rocbillow.core.uikit.extension

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.rocbillow.core.assist.ContextProvider

val @receiver:ColorRes Int.colorInt
    get() = ContextCompat.getColor(ContextProvider.context, this)