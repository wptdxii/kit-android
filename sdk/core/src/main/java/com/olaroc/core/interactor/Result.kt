package com.olaroc.core.interactor

import com.olaroc.core.network.BusinessException
import com.olaroc.core.network.RESPONSE_CODE_UNAUTHORIZED
import com.olaroc.core.network.ResponseException
import com.olaroc.core.network.toResponseException
import kotlinx.coroutines.flow.MutableStateFlow

sealed class Result<out R> {
    data class Success<out T>(val data: T, val message: String? = null) : Result<T>()
    data class Failure(val e: Throwable) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Failure -> "Error[exception=$e]"
            is Loading -> "Loading"
        }
    }
}

inline fun Result<*>.onLoading(onLoading: () -> Unit) {
    if (this is Result.Loading) onLoading()
}

inline fun <R> Result<R>.onSuccess(onSuccess: (R, message: String?) -> Unit) {
    if (this is Result.Success) onSuccess(data, message)
}

inline fun Result<*>.onFailure(onFailure: (ResponseException) -> Unit) {
    if (this is Result.Failure) onFailure(e.toResponseException())
}

inline fun Result<*>.onUnauthorized(onUnauthorized: () -> Unit) {
    if (this !is Result.Failure) return
    if (e !is BusinessException) return
    if (e.code != RESPONSE_CODE_UNAUTHORIZED) return
    onUnauthorized()
}

val Result<*>.succeed
    get() = (this is Result.Success) && data != null


val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data

val <T> Result<T>.message: String?
    get() = when (this) {
        is Result.Success -> message
        is Result.Failure -> e.message
        else -> null
    }

fun <T> Result<T>.successOr(fallback: T) =
    (this as? Result.Success)?.data ?: fallback

fun <T> Result<T>.updateOnSuccess(stateFlow: MutableStateFlow<T>) {
    if (this is Result.Success) {
        stateFlow.value = data
    }
}