package com.rocbillow.core.extension

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author rocbillow
 * @date 2021-01-07
 */

/**
 * Scheduling the rx stream from io thread to android main thread.
 */
fun <T> mainThreadTransformer() = ObservableTransformer<T, T> {
  it.subscribeOn(Schedulers.io())
    .observeOn(AndroidSchedulers.mainThread())
}