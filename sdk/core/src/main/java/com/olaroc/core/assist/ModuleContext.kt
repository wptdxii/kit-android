package com.olaroc.core.assist

import android.annotation.SuppressLint
import android.content.Context

/**
 * Singleton app module context holder.
 */
@SuppressLint("StaticFieldLeak")
class ModuleContext private constructor(builder: Builder) {

    val baseUrl = builder.baseUrl
    val context = builder.context
    val isDebug = builder.isDebug

    companion object {

        @Volatile
        @JvmStatic
        private var instance: ModuleContext? = null

        @JvmStatic
        val INSTANCE: ModuleContext
            get() {
                if (instance == null) {
                    throw InstantiationException("Please call ModuleContext.init first")
                }
                return instance as ModuleContext
            }


        @JvmStatic
        fun init(moduleContext: ModuleContext) {
            instance ?: synchronized(this) {
                instance ?: moduleContext.also { instance = it }
            }
        }
    }

    class Builder {

        var isDebug = false
        lateinit var context: Context
        lateinit var baseUrl: String

        fun isDebug(isDebug: Boolean): Builder {
            this.isDebug = isDebug
            return this
        }

        fun context(context: Context): Builder {
            this.context = context.applicationContext
            return this
        }

        fun baseUrl(baseUrl: String): Builder {
            this.baseUrl = baseUrl
            return this
        }

        fun build() = ModuleContext(this)
    }
}