package com.cyl.wandroid.tools

import java.text.SimpleDateFormat
import java.util.*

fun millisSecondsToDateString(millisSeconds: Long, format: String = "yyyy-MM-dd HH:mm:ss"): String {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(Date(millisSeconds))
}