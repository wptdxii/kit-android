package com.rocbillow.base.extension

import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @author rocbillow
 * @date 2021-01-22
 */

fun <T : ViewDataBinding> AppCompatActivity.dataBinding(@LayoutRes resId: Int): Lazy<T> = lazy {
  DataBindingUtil.setContentView(this, resId)
}

fun <T : ViewDataBinding> Fragment.dataBinding() = AutoClearedReadWriteDelete<T>(this)

fun <T : ViewDataBinding> Fragment.dataBinding(bindingFactory: (View) -> T) =
  AutoClearedReadOnlyDelegate(this, bindingFactory)