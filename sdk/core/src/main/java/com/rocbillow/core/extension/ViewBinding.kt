package com.rocbillow.core.extension

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

fun <T : ViewBinding> Dialog.viewBinding(bindingFactory: (LayoutInflater) -> T) = lazy {
  bindingFactory(layoutInflater).apply { setContentView(root) }
}

/**
 * ViewBinding delegate for Fragment constructed by empty constructor.
 */
fun <T : ViewBinding> Fragment.viewBinding() = AutoClearedReadWriteDelete<T>(this)

/**
 * ViewBinding delegate for Fragment constructed by constructor with layout id.
 */
fun <T : ViewBinding> Fragment.viewBinding(bindingFactory: (View) -> T) =
  AutoClearedReadOnlyDelegate(this, bindingFactory)