package com.olaroc.core.initializer

import com.olaroc.core.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import timber.log.Timber

/**
 * @author olaroc
 * @date 2020-12-23
 */
class TimberDebugTree : Timber.DebugTree() {

    companion object {
        private const val TAG = "Timber"
        private const val METHOD_COUNT = 1
        private const val METHOD_OFFSET = 4
    }

    init {
        Logger.addLogAdapter(createLogAdapter())
    }

    private fun createLogAdapter(): LogAdapter {
        return object : AndroidLogAdapter(createPrettyFormatStrategy()) {
            override fun isLoggable(priority: Int, tag: String?) = BuildConfig.DEBUG
        }
    }

    private fun createPrettyFormatStrategy(): PrettyFormatStrategy {
        return PrettyFormatStrategy.newBuilder()
            .methodCount(METHOD_COUNT)
            .methodOffset(METHOD_OFFSET)
            .tag(TAG)
            .build()
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (message.isJson()) {
            Logger.t(tag)
            Logger.json(message)
            return
        }
        Logger.log(priority, tag, message, t)
    }

    private fun String.isJson(): Boolean {
        val result = trimIndent()
        if (result.isNotJsonObject() && result.isNotJsonArray()) return false
        return runCatching {
            val nextValue = JSONTokener(result).nextValue()
            nextValue is JSONObject || nextValue is JSONArray
        }.getOrElse {
            false
        }
    }


    private fun String.isNotJsonObject(): Boolean =
        !(startsWith("{") && endsWith("}"))

    private fun String.isNotJsonArray(): Boolean =
        !(startsWith("[") && endsWith("]"))
}