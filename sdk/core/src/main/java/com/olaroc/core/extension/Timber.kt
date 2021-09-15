package com.olaroc.core.extension

import com.orhanobut.logger.Logger
import timber.log.Timber

private const val TAG = "Json"

fun Timber.Forest.json(json: String?) {
    Logger.t(TAG)
    Logger.json(json)
}