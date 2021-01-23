package com.rocbillow.base.generic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author rocbillow
 * @date 2020-09-04
 */

abstract class BaseActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    immersion()
  }

  protected open fun immersion() {
    immersionBar {
      statusBarDarkFont(true)
      navigationBarDarkIcon(true)
      navigationBarColor(android.R.color.white)
    }
  }
}
