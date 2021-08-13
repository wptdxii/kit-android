package com.olaroc.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.olaroc.core.systembar.applyLightTheme
import com.olaroc.core.systembar.applyNotFitsSystemWindows

/**
 * @author olaroc
 * @date 2020-09-04
 */

abstract class BaseActivity(@LayoutRes resId: Int) : AppCompatActivity(resId) {

    constructor() : this(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        applySystemWindows()
    }

    protected open fun applySystemWindows() {
        applyNotFitsSystemWindows()
        applyLightTheme()
    }
}
