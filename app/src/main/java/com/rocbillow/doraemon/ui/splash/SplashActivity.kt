package com.rocbillow.doraemon.ui.splash

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.rocbillow.core.base.BaseActivity
import com.rocbillow.doraemon.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

/**
 * @author rocbillow
 * @date 2020-08-07
 */

private const val PERSISTENT_PERIOD = 500L

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    window.setBackgroundDrawable(null)
    super.onCreate(savedInstanceState)
    navToMainActivity()
  }

  private fun navToMainActivity() {
    lifecycleScope.launchWhenStarted {
      delay(PERSISTENT_PERIOD)
      MainActivity.start(this@SplashActivity)
      finish()
    }
  }

  override fun immersion() {
    // ignore
  }
}
