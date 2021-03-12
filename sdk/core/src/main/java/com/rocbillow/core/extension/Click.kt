package com.rocbillow.core.extension

import android.view.View
import androidx.core.view.postDelayed
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

const val THROTTLE_DURATION = 1500L

inline fun View.setOnThrottleClickListenerByRx(crossinline onClick: (View) -> Unit): Disposable =
  clicks()
    .throttleFirst(THROTTLE_DURATION, TimeUnit.MILLISECONDS)
    .subscribe { onClick(this) }

inline fun View.setOnThrottleClickListenerByClickable(crossinline onClick: (View) -> Unit) {
  setOnClickListener {
    onClick(it)
    with(it) {
      isClickable = false
      postDelayed(THROTTLE_DURATION) {
        isClickable = true
      }
    }
  }
}

inline fun View.setOnThrottleClickListener(crossinline onClick: (View) -> Unit) {
  var lastTimeMillis = 0L
  val currentTimeMillis = System.currentTimeMillis()
  if (currentTimeMillis - lastTimeMillis > THROTTLE_DURATION) {
    setOnClickListener {
      onClick(it)
      lastTimeMillis = System.currentTimeMillis()
    }
  }
}

inline fun View.setOnDoubleClickListener(crossinline onClick: (View) -> Unit) {
  var isClickedOnce = false
  setOnClickListener {
    if (!isClickedOnce) {
      isClickedOnce = true
      postDelayed(1000L) {
        isClickedOnce = false
      }
      return@setOnClickListener
    }
    onClick(this)
  }
}