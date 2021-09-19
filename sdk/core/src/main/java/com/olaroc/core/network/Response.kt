package com.olaroc.core.network

import com.olaroc.core.interactor.Result
import com.olaroc.core.network.ResponseFailure.BusinessFailure
import com.squareup.moshi.Json

class Response<out T>(
    @field:Json(name = "code") val code: Int = 0,
    @field:Json(name = "message") val message: String? = null,
    @field:Json(name = "data") val data: T
)

fun <T> Response<T>.result(): Result<T> = when (code) {
    RESPONSE_CODE_SUCCESS -> Result.Success(data, message)
    else -> Result.Failure(BusinessFailure(code, message))
}