package com.cyl.wandroid.http.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.cyl.wandroid.base.App
import com.cyl.wandroid.tools.isNetConnect
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!isNetConnect()) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }

        val response = chain.proceed(request)
        if (isNetConnect()) {
            val maxState = 0
            response.newBuilder().apply {
                header("Cache-Control", "public, max-age=$maxState")
                removeHeader("Pragma")
                build()
            }
        } else {
            val maxState = 60 * 60 * 24 * 21
            response.newBuilder().apply {
                header("Cache-Control", "public, only-if-cached, max-stale=$maxState")
                removeHeader("Pragma")
                build()
            }
        }
        return response
    }
}