package com.dxii.basekit.ext

import android.content.Intent
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author idxii
 * @date 2020-09-04
 */

fun AppCompatActivity.startActivity(clazz: Class<out AppCompatActivity>) {
    startActivity(Intent(this, clazz))
}

fun <T : ViewDataBinding> AppCompatActivity.binding(@LayoutRes resId: Int): Lazy<T> = lazy {
    DataBindingUtil.setContentView(this, resId)
}
