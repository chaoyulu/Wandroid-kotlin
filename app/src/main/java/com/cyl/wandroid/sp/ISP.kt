package com.cyl.wandroid.sp

interface ISP {
    fun <T> setValue(key: String, value: T)

    fun <T> getValue(key: String, defaultValue: T): T

    fun removeOf(key: String)

    fun removeAll()
}