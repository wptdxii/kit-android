package com.olaroc.core.interactor

import autodispose2.SingleSubscribeProxy
import autodispose2.interop.coroutines.autoDispose
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineScope


abstract class RxUseCase<in P, R>(private val scheduler: Scheduler) {

    abstract fun execute(params: P): Single<Result<R>>

    operator fun invoke(
        viewModelScope: CoroutineScope,
        params: P
    ): SingleSubscribeProxy<Result<R>> =
        execute(params)
            .onErrorResumeNext { Single.just(Result.Failure(it)) }
            .observeOn(scheduler)
            .subscribeOn(AndroidSchedulers.mainThread())
            .autoDispose(viewModelScope)
}