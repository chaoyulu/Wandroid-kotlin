package com.cyl.wandroid.tools

import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT_1 = "yyyy-MM-dd HH:mm:ss"
const val DATE_FORMAT_2 = "yyyy-MM-dd"

// 毫秒转日期字符串
fun millisSecondsToDateString(millisSeconds: Long, format: String = DATE_FORMAT_1): String {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(Date(millisSeconds))
}

// 日期字符串转Date
fun dateStringToDate(dateString: String, format: String = DATE_FORMAT_1): Date? {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.parse(dateString)
}

// 毫秒转Date
fun millisSecondsToDate(millisSeconds: Long, format: String = DATE_FORMAT_1): Date? {
    val s = millisSecondsToDateString(millisSeconds, format)
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.parse(s)
}