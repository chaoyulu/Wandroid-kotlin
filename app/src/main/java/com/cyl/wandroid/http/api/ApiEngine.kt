package com.cyl.wandroid.http.api

import android.util.Log
import com.cyl.wandroid.base.App
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.UnsupportedEncodingException
import java.util.concurrent.TimeUnit

object ApiEngine {
    private const val DEFAULT_TIME_OUT = 10L
    private var retrofit: Retrofit
    val cookieJar =
        PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(App.app))

    init {
        val httpLogging =
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                try {
//                    val msg = URLDecoder.decode(it, "utf-8")
                    Log.e("OKHttpLog", it)
                } catch (e: UnsupportedEncodingException) {
                    Log.e("OKHttpLog", it)
                }
            })

        httpLogging.level = HttpLoggingInterceptor.Level.BODY
        val size = 1024 * 1024 * 100L
        val cacheFile = File(App.app.cacheDir, "OKHttpCache")
        val cache = Cache(cacheFile, size)

        val client = OkHttpClient.Builder().apply {
            connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
            addNetworkInterceptor(NetworkInterceptor())
            addInterceptor(httpLogging)
            cache(cache)
            callTimeout(10, TimeUnit.SECONDS)
            cookieJar(cookieJar)
        }.build()

        retrofit = Retrofit.Builder().apply {
            baseUrl(ApiService.BASE_URL)
            client(client)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }

    fun getApiService(): ApiService = retrofit.create(ApiService::class.java)
}