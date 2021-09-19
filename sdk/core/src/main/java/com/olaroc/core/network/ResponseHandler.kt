package com.olaroc.core.network

import com.olaroc.core.network.ResponseFailure.BusinessFailure
import com.olaroc.core.network.ResponseFailure.UnexpectedFailure
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLException
import javax.net.ssl.SSLHandshakeException

sealed class ResponseFailure(message: String?) : Throwable(message) {

    class BusinessFailure(val code: Int, message: String?) : ResponseFailure(message)

    class UnexpectedFailure(message: String?) : ResponseFailure(message)
}

fun Throwable.responseFailure(): ResponseFailure = when (this) {
    is BusinessFailure -> this
    is HttpException -> UnexpectedFailure(message())
    is SocketTimeoutException -> UnexpectedFailure("Time out")
    is SSLException -> UnexpectedFailure("SSL error")
    is SSLHandshakeException -> UnexpectedFailure("SSL hand shake error")
    else -> UnexpectedFailure(message ?: "Unexpected failure")
}