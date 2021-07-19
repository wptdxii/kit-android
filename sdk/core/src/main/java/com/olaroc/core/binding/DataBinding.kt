package com.olaroc.core.binding

import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @author olaroc
 * @date 2021-01-22
 */

fun <T : ViewDataBinding> AppCompatActivity.dataBinding(@LayoutRes resId: Int): Lazy<T> = lazy {
  DataBindingUtil.setContentView(this, resId)
}

/**
 * DataBinding delegate for Fragment constructed by empty constructor.
 */
fun <T : ViewDataBinding> Fragment.dataBinding() = AutoClearedReadWriteDelete<T>(this)

/**
 * DataBinding delegate for Fragment constructed by constructor with layout id.
 */
fun <T : ViewDataBinding> Fragment.dataBinding(bindingFactory: (View) -> T) =
  AutoClearedReadOnlyDelegate(this, bindingFactory)