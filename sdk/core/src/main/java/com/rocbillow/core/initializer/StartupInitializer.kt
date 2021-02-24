package com.rocbillow.core.initializer

import android.content.Context
import androidx.startup.Initializer
import com.rocbillow.core.BuildConfig
import com.rocbillow.core.utils.ContextProvider
import timber.log.Timber

/**
 * @author rocbillow
 * @date 2020-09-10
 */
class StartupInitializer : Initializer<Unit> {

  override fun create(context: Context) {
    startupContextProvider(context)
    startupTimber()
  }

  override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

  private fun startupContextProvider(context: Context) {
    ContextProvider.attach(context)
  }

  private fun startupTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(TimberDebugTree())
    } else {
      Timber.plant(TimberReleaseTree())
    }
  }
}
