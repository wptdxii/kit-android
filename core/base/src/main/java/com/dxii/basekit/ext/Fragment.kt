package com.dxii.basekit.ext

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

/**
 * @author idxii
 * @date 2020-09-07
 */

fun <T : ViewDataBinding> binding(
    inflater: LayoutInflater,
    @LayoutRes resId: Int,
    container: ViewGroup?,
): T = DataBindingUtil.inflate(inflater, resId, container, false)
