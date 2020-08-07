package com.cyl.wandroid.tools

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.os.Build
import androidx.annotation.RequiresApi
import com.cyl.wandroid.base.App

@RequiresApi(Build.VERSION_CODES.M)
fun isNetConnect(): Boolean {
    val connectivity =
        App.app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info: NetworkInfo? = connectivity.activeNetworkInfo
    if (info != null && info.isConnected) {
        return info.state == NetworkInfo.State.CONNECTED
    }
    return false
}