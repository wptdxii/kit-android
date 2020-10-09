package com.dxii.doraemon.ui.splash

import android.os.Bundle
import com.dxii.doraemon.ui.main.MainActivity
import com.dxii.basekit.base.BaseActivity
import com.dxii.basekit.ext.startActivity
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * @author idxii
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
            startActivity(MainActivity::class.java)
            finish()
        }
    }
}