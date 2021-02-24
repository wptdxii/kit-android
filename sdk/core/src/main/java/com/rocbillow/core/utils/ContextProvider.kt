package com.rocbillow.core.utils

import android.annotation.SuppressLint
import android.content.Context

/**
 * Provide application context
 * @author rocbillow
 * @date 2020-09-11
 */
@SuppressLint("StaticFieldLeak")
object ContextProvider {

  lateinit var context: Context

  fun attach(context: Context) {
    this.context = context.applicationContext
  }
}
