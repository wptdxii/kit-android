package com.rocbillow.core.extension

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.rocbillow.core.assist.ContextProvider


fun inflate(@LayoutRes resId: Int, root: ViewGroup? = null): View =
  LayoutInflater.from(ContextProvider.context).inflate(resId, root)