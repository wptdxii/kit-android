package com.olaroc.core.assist

import android.annotation.SuppressLint
import android.content.Context

/**
 * Provide application context
 * @author olaroc
 * @date 2020-09-11
 */
@SuppressLint("StaticFieldLeak")
object ContextProvider {

    lateinit var context: Context
        private set

    fun attach(context: Context) {
        if (this::context.isInitialized) return
        this.context = context
    }
}
