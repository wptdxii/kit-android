package com.dxii.basekit.utils

import android.content.Context

/**
 * Provide application context
 * @author idxii
 * @date 2020-09-11
 */
object ContextProvider {

    lateinit var context: Context

    fun attach(context: Context) {
        this.context = context.applicationContext
    }
}