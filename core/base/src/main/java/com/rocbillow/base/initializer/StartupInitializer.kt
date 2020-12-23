package com.rocbillow.base.initializer

import android.content.Context
import androidx.startup.Initializer
import com.rocbillow.base.BuildConfig
import com.rocbillow.base.utils.ContextProvider
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
