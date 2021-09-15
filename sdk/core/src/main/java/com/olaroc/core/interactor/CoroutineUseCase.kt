package com.olaroc.core.interactor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class CoroutineUseCase<in P, out R>(private val dispatcher: CoroutineDispatcher) {

    abstract suspend fun execute(params: P): Result<R>

    suspend operator fun invoke(
        params: P
    ): Result<R> = runCatching {
        withContext(dispatcher) { execute(params) }
    }.getOrElse { Result.Failure(it) }
}

inline fun <P, R> ViewModel.launchUseCase(
    params: P,
    useCase: CoroutineUseCase<P, R>,
    crossinline action: Result<R>.() -> Unit
) {
    viewModelScope.launch { useCase(params).action() }
}