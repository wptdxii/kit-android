package com.arch.sunflower.ui

import android.content.Context
import android.os.Bundle
import com.arch.sunflower.R
import com.arch.sunflower.databinding.ActivityGardenBinding
import com.rocbillow.core.base.BaseActivity
import com.rocbillow.core.binding.dataBinding
import com.rocbillow.core.extension.start
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author rocbillow
 * @date 2020-08-07
 */

@AndroidEntryPoint
class GardenActivity : BaseActivity() {

  companion object {
    const val TAG = "Sunflower"
    fun start(context: Context) {
      context.start<GardenActivity>()
    }
  }

  private val dataBinding by dataBinding<ActivityGardenBinding>(R.layout.activity_garden)

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    dataBinding.executePendingBindings()
  }
}
