package com.olaroc.core.initializer

import android.content.Context
import androidx.startup.Initializer
import com.olaroc.core.assist.ContextProvider
import com.olaroc.core.extension.isDebug
import timber.log.Timber

/**
 * @author olaroc
 * @date 2020-09-10
 */
class StartupInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        initContextProvider(context)
        initTimber(context.isDebug)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

    private fun initContextProvider(context: Context) {
        ContextProvider.attach(context)
    }

    private fun initTimber(debug: Boolean) {
        val tree = if (debug) TimberDebugTree() else TimberReleaseTree()
        Timber.plant(tree)
    }
}
