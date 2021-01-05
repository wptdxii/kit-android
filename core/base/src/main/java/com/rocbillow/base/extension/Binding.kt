package com.rocbillow.base.extension

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @author rocbillow
 * @date 2020-09-04
 */

fun <T : ViewDataBinding> AppCompatActivity.dataBinding(@LayoutRes resId: Int): Lazy<T> = lazy {
  DataBindingUtil.setContentView(this, resId)
}

fun <T : ViewDataBinding> Fragment.dataBinding(
  inflater: LayoutInflater,
  @LayoutRes resId: Int,
  container: ViewGroup?,
): T = DataBindingUtil.inflate(inflater, resId, container, false)
