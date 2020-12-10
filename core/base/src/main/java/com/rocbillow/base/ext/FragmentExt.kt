package com.rocbillow.base.ext

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @author rocbillow
 * @date 2020-09-07
 */

fun <T : ViewDataBinding> Fragment.binding(
  inflater: LayoutInflater,
  @LayoutRes resId: Int,
  container: ViewGroup?,
): T = DataBindingUtil.inflate(inflater, resId, container, false)
