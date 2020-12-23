package com.rocbillow.base.initializer

import android.util.Log
import timber.log.Timber

/**
 * @author rocbillow
 * @date 2020-12-23
 */
class TimberReleaseTree : Timber.Tree() {

  override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
    if (priority == Log.VERBOSE || priority == Log.DEBUG) return

    // TODO: 12/23/20 report crash
  }
}