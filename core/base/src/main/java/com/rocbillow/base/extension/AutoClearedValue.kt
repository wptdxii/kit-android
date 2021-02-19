package com.rocbillow.base.extension

import android.view.View
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
 * @author rocbillow
 * @date 2020-09-07
 */
class AutoClearedReadWriteDelete<T : ViewBinding>(private val fragment: Fragment) :
  ReadWriteProperty<Fragment, T> {

  private var _value: T? = null

  init {
    fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {

      val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
        val viewLifecycleOwner = it ?: return@Observer

        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
          override fun onDestroy(owner: LifecycleOwner) {
            _value = null
          }
        })
      }

      override fun onCreate(owner: LifecycleOwner) {
        fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerLiveDataObserver)
      }

      override fun onDestroy(owner: LifecycleOwner) {
        fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerLiveDataObserver)
      }
    })
  }

  override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
    _value = value
  }

  override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
    return _value ?: throw IllegalStateException(
      "should never call auto-cleared-value get when it might not be available"
    )
  }
}

class AutoClearedReadOnlyDelegate<T>(
  private val fragment: Fragment,
  private val bindingFactory: (View) -> T,
) : ReadOnlyProperty<Fragment, T> {

  private var _value: T? = null

  init {

    fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {

      val viewLifecycleOwnerLiveDataObserver = Observer<LifecycleOwner?> {
        val viewLifecycleOwner = it ?: return@Observer

        viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
          override fun onDestroy(owner: LifecycleOwner) {
            _value = null
          }
        })
      }

      override fun onCreate(owner: LifecycleOwner) {
        fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerLiveDataObserver)
      }

      override fun onDestroy(owner: LifecycleOwner) {
        fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerLiveDataObserver)
      }
    })
  }

  override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
    val value = _value
    if (value != null) {
      return value
    }

    if (!thisRef.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
      throw IllegalStateException(
        "should never call auto-cleared-value get when it might not be available"
      )
    }

    return bindingFactory(thisRef.requireView()).also { _value = it }
  }
}