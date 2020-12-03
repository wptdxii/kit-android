package com.dxii.basekit.ext

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * @author idxii
 * @date 2020-09-07
 */

fun Fragment.startActivity(clazz: Class<out AppCompatActivity>) {
    startActivity(Intent(requireContext(), clazz))
}

fun <T : ViewDataBinding> binding(
    inflater: LayoutInflater,
    @LayoutRes resId: Int,
    container: ViewGroup?,
): T = DataBindingUtil.inflate(inflater, resId, container, false)
