package com.rocbillow.base.extension

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author rocbillow
 * @date 2020-09-07
 */
class AutoClearedReadWriteDelete<T : Any> : ReadWriteProperty<Fragment, T> {

  private var _value: T? = null

  override fun setValue(thisRef: Fragment, property: KProperty<*>, value: T) {
    _value = value
    thisRef.lifecycle.addObserver(object : DefaultLifecycleObserver {
      override fun onCreate(owner: LifecycleOwner) {
        thisRef.viewLifecycleOwnerLiveData.observe(thisRef) {
          it?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
            override fun onDestroy(owner: LifecycleOwner) {
              _value = null
            }
          })
        }
      }
    })
  }

  override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
    return _value ?: throw IllegalStateException(
      "should never call auto-cleared-value get when it might not be available"
    )
  }
}

class AutoClearedReadOnlyDelegate<T>(private val block: (View) -> T) :
  ReadOnlyProperty<Fragment, T> {

  private var isInitialized = false
  private var _value: T? = null

  override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
    if (!isInitialized) {
      thisRef.lifecycle.addObserver(object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
          thisRef.viewLifecycleOwnerLiveData.observe(thisRef) {
            it?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
              override fun onDestroy(owner: LifecycleOwner) {
                _value = null
              }
            })
          }
        }
      })
      _value = block(thisRef.requireView())
      isInitialized = true
    }
    return _value ?: throw IllegalStateException(
      "should never call auto-cleared-value get when it might not be available"
    )
  }
}

