package com.olaroc.core.binding

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author olaroc
 * @date 2020-09-07
 */

abstract class AutoCleared<T : ViewBinding>(private val fragment: Fragment) {

    protected open var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {

            val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
                val viewLifecycleOwner = it ?: return@Observer

                viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                    override fun onDestroy(owner: LifecycleOwner) {
                        binding = null
                    }
                })
            }

            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(
                    viewLifecycleOwnerLiveDataObserver
                )
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(
                    viewLifecycleOwnerLiveDataObserver
                )
            }
        })
    }
}

class AutoClearedReadWriteDelete<T : ViewBinding>(fragment: Fragment) :
    AutoCleared<T>(fragment), ReadWriteProperty<Fragment, T> {

    override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
        if (value is ViewDataBinding) {
            value.lifecycleOwner = thisRef.viewLifecycleOwner
        }
        binding = value
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return binding ?: throw IllegalStateException(
            "should never call auto-cleared-value get when it might not be available"
        )
    }
}

class AutoClearedReadOnlyDelegate<T : ViewBinding>(
    fragment: Fragment,
    private val bindingFactory: (View) -> T,
) : AutoCleared<T>(fragment), ReadOnlyProperty<Fragment, T> {

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        val value = binding
        if (value != null) return value

        if (!thisRef.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException(
                "should never call auto-cleared-value get when it might not be available"
            )
        }

        return bindingFactory(thisRef.requireView())
            .apply { bindLifecycleOwner(thisRef) }
            .also { binding = it }
    }

    private fun T.bindLifecycleOwner(thisRef: Fragment) {
        if (this is ViewDataBinding) {
            lifecycleOwner = thisRef.viewLifecycleOwner
        }
    }
}