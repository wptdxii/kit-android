package com.olaroc.core.binding

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * @author olaroc
 * @date 2020-09-04
 */
inline fun <T : ViewBinding> Activity.viewBinding(crossinline block: (LayoutInflater) -> T) = lazy {
    block(layoutInflater).apply { setContentView(root) }
}

inline fun <T : ViewBinding> Activity.bindView(crossinline block: (View) -> T) = lazy {
    val contentView = window.decorView.findViewById(android.R.id.content) as ViewGroup
    block(contentView.getChildAt(0))
}

inline fun <T : ViewBinding> Dialog.viewBinding(crossinline bindingFactory: (LayoutInflater) -> T) =
    lazy {
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