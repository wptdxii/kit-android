package com.rocbillow.base.extension

import android.view.View
import com.jakewharton.rxbinding4.view.clicks
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author rocbillow
 * @date 2021-01-07
 */

/**
 * Debounce the click event by RxBinding.
 */
fun View.setOnThrottleClickListener(onClick: (View) -> Unit): Disposable = clicks()
  .throttleFirst(1500, TimeUnit.MILLISECONDS)
  .subscribe {
    onClick(this)
  }

/**
 * Scheduling the rx stream from io thread to android main thread.
 */
fun <T> io2main() = ObservableTransformer<T, T> {
  it.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
}