package com.olaroc.core.interactor

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, out R>(private val dispatcher: CoroutineDispatcher) {

    abstract suspend fun execute(params: P): Flow<Result<R>>

    suspend operator fun invoke(params: P): Flow<Result<R>> = execute(params)
        .catch { Result.Failure(it) }
        .flowOn(dispatcher)
}