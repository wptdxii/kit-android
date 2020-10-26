package com.dxii.basekit.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author idxii
 * @date 2020-09-04
 */

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindBars()
    }

    protected open fun bindBars() {
        immersionBar {
            statusBarDarkFont(true)
        }
    }
}

