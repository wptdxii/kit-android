package com.dxii.basekit.initializer

import android.content.Context
import androidx.startup.Initializer
import com.dxii.basekit.BuildConfig
import com.dxii.basekit.utils.ContextProvider
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.LogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

/**
 * @author idxii
 * @date 2020-09-10
 */
class FramekitInitializer : Initializer<Unit> {

    companion object {
        private const val TAG = "Timber"
        private const val METHOD_COUNT = 1
        private const val METHOD_OFFSET = 5
    }

    override fun create(context: Context) {
        startupContextProvider(context)
        startupTimber()
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()

    private fun startupContextProvider(context: Context) {
        ContextProvider.attach(context)
    }

    private fun startupTimber() {
        Logger.addLogAdapter(createLogAdapter())

        Timber.plant(object : Timber.DebugTree() {
            override fun isLoggable(tag: String?, priority: Int): Boolean = BuildConfig.DEBUG

            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                if (!isJson(message)) {
                    Logger.log(priority, tag, message, t)
                } else {
                    Logger.t(tag)
                    Logger.json(message)
                }
            }
        })
    }

    private fun createLogAdapter(): LogAdapter {
        val strategy = PrettyFormatStrategy.newBuilder()
            .methodCount(METHOD_COUNT)
            .methodOffset(METHOD_OFFSET)
            .tag(TAG)
            .build()
        return object : AndroidLogAdapter(strategy) {
            override fun isLoggable(priority: Int, tag: String?) = BuildConfig.DEBUG
        }
    }

    private fun isJson(message: String): Boolean = try {
        JSONObject(message)
        true
    } catch (e: JSONException) {
        false
    }
}