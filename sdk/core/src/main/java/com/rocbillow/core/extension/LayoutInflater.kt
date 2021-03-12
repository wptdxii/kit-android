package com.rocbillow.core.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.rocbillow.core.assist.ContextProvider


fun inflate(@LayoutRes resId: Int, root: ViewGroup? = null) =
  LayoutInflater.from(ContextProvider.context).inflate(resId, root)