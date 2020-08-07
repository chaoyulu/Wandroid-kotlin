package com.cyl.wandroid.common.bus

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.jeremyliao.liveeventbus.LiveEventBus

object Bus {
    // 发送
    inline fun <reified T> post(key: String, value: T) {
        LiveEventBus.get(key, T::class.java).post(value)
    }

    // 订阅
    inline fun <reified T> observe(
        key: String,
        owner: LifecycleOwner,
        crossinline observer: ((value: T) -> Unit)
    ) {
        LiveEventBus.get(key, T::class.java).observe(owner, Observer {
            observer(it)
        })
    }
}