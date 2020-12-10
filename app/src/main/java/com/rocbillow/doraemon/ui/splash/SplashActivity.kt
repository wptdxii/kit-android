package com.rocbillow.doraemon.ui.splash

import android.os.Bundle
import com.rocbillow.base.base.BaseActivity
import com.rocbillow.doraemon.ui.main.MainActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author rocbillow
 * @date 2020-08-07
 */
class SplashActivity : BaseActivity() {

  companion object {
    private const val PERSISTENT_PERIOD = 500L
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    window.setBackgroundDrawable(null)
    super.onCreate(savedInstanceState)
    MainScope().launch {
      delay(PERSISTENT_PERIOD)
      MainActivity.start(this@SplashActivity)
      finish()
    }
  }

  override fun immersion() {
    // ignore
  }
}
