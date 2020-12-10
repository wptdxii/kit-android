package com.rocbillow.base.ext

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author rocbillow
 * @date 2020-09-04
 */

fun <T : ViewDataBinding> AppCompatActivity.binding(@LayoutRes resId: Int): Lazy<T> = lazy {
  DataBindingUtil.setContentView(this, resId)
}
