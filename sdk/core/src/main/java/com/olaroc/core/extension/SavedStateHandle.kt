package com.olaroc.core.extension

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlin.properties.ReadOnlyProperty
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author olaroc
 * @date 2021-01-15
 */

class SavedStateKeyValueDelegate<T>(
  private val savedStateHandle: SavedStateHandle,
  private val key: String,
  private val initialValue: T,
) : ReadWriteProperty<ViewModel, T> {

  override fun setValue(thisRef: ViewModel, property: KProperty<*>, value: T) {
    savedStateHandle[key] = value
  }

  override fun getValue(thisRef: ViewModel, property: KProperty<*>): T =
    savedStateHandle[key] ?: initialValue
}

class SavedStateLiveDataDelegate<T>(
  private val savedStateHandle: SavedStateHandle,
  private val key: String,
  private val initialValue: T? = null,
) : ReadOnlyProperty<ViewModel, MutableLiveData<T>> {

  override fun getValue(thisRef: ViewModel, property: KProperty<*>): MutableLiveData<T> =
    savedStateHandle.getLiveData(key, initialValue)
}