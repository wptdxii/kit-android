package com.rocbillow.core.livedata

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean

/**
 *
 * @author rocbillow
 * @date 2020-09-04
 */
class SingleLiveEvent<T> : MutableLiveData<T?>() {

  companion object {
    private const val TAG = "SingleLiveEvent"
  }

  private val pending = AtomicBoolean(false)

  @MainThread
  override fun observe(owner: LifecycleOwner, observer: Observer<in T?>) {
    if (hasActiveObservers()) {
      Timber.w("Multiple observers registered but only one will be notified of changes.")
    }

    // Observe the internal MutableLiveData
    super.observe(owner) {
      if (pending.compareAndSet(true, false)) observer.onChanged(it)
    }
  }

  @MainThread
  override fun setValue(value: T?) {
    pending.set(true)
    super.setValue(value)
  }

  @MainThread
  fun call() {
    value = null
  }
}
