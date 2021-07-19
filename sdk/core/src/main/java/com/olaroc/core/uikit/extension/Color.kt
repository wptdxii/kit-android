package com.olaroc.core.uikit.extension

import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.olaroc.core.assist.ContextProvider

val @receiver:ColorRes Int.colorInt
    get() = ContextCompat.getColor(ContextProvider.context, this)