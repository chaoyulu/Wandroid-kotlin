package com.cyl.wandroid.tools

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.cyl.wandroid.base.App

/**
 * 和系统功能相关
 * */

/**
 * 复制文本到剪切板
 */
fun copyText(text: CharSequence, label: String = "") {
    if (text.isEmpty()) return
    val cbs =
        App.app.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    cbs.setPrimaryClip(ClipData.newPlainText(label, text))
}