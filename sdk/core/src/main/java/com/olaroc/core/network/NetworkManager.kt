package com.olaroc.core.network

import android.content.Context
import com.olaroc.core.assist.ContextProvider
import com.olaroc.core.assist.ModuleContext
import com.olaroc.core.extension.isDebug
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

private const val CONNECT_TIME_OUT = 30_000L
private const val READ_TIME_OUT = 30_000L
private const val CACHE_PATH = "HttpResponseCache"
private const val CACHE_SIZE = 10 * 1204 * 1024L

object NetworkManager {

    val okHttpClient by lazy {
        val context = ContextProvider.context
        val builder = OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIME_OUT, TimeUnit.MILLISECONDS)
            readTimeout(READ_TIME_OUT, TimeUnit.MILLISECONDS)
            cache(getCache(context))
        }
        if (context.isDebug) {
            builder.addInterceptor(getLoggingInterceptor())
        }
        builder.build()
    }

    private fun getCache(context: Context): Cache {
        val cacheDir = File(context.cacheDir, CACHE_PATH)
        return Cache(cacheDir, CACHE_SIZE)
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val timber = HttpLoggingInterceptor.Logger { Timber.d(it) }
        val loggingInterceptor = HttpLoggingInterceptor(timber).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return loggingInterceptor
    }

    val retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(okHttpClient)
            .baseUrl(ModuleContext.INSTANCE.baseUrl)
            .build()
    }
}