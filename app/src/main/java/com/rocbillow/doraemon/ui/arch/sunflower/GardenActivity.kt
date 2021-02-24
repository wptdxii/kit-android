package com.rocbillow.doraemon.ui.arch.sunflower

import android.content.Context
import android.os.Bundle
import com.rocbillow.core.extension.start
import com.rocbillow.core.base.BaseActivity

/**
 * @author rocbillow
 * @date 2020-08-07
 */

class GardenActivity : BaseActivity() {

  companion object {
    const val TAG = "Sunflower"
    fun start(context: Context) {
      context.start<GardenActivity>()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }
}
