package com.rocbillow.base.extension

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * @author rocbillow
 * @date 2020-09-04
 */
fun <T : ViewBinding> Activity.viewBinding(block: (LayoutInflater) -> T) = lazy {
  block(layoutInflater).apply { setContentView(root) }
}

fun <T : ViewBinding> Fragment.viewBinding() = AutoClearedReadWriteDelete<T>()

fun <T : ViewBinding> Fragment.viewBinding(block: (View) -> T) = AutoClearedReadOnlyDelegate(block)

fun <T : ViewBinding> Dialog.viewBinding(block: (LayoutInflater) -> T) = lazy {
  block(layoutInflater).apply { setContentView(root) }
}

