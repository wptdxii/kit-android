package com.olaroc.doraemon.initializer

import android.content.Context
import androidx.startup.Initializer
import com.olaroc.core.assist.ModuleContext
import com.olaroc.doraemon.BuildConfig

class AppInitializer : Initializer<Unit> {

    override fun create(context: Context) = ModuleContext.init(
        ModuleContext.Builder()
            .context(context)
            .baseUrl(BuildConfig.BASE_URL)
            .isDebug(true)
            .build()
    )

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}