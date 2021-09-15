package com.olaroc.core.network

import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException

class BusinessException(val code: Int, message: String?) : Throwable(message)

class ResponseException(message: String?) : Throwable(message)

fun BusinessException.toResponseException() = ResponseException(message)

fun Throwable.toResponseException(): ResponseException = when (this) {
    is BusinessException -> this.toResponseException()
    is HttpException -> ResponseException(message())
    is SocketTimeoutException -> ResponseException("Time out")
    is SSLException -> ResponseException("SSL error")
    is SSLHandshakeException -> ResponseException("SSL hand shake error")
    else -> ResponseException(message ?: "Unexpected error")
}