package com.cyl.wandroid.ext

import androidx.core.text.HtmlCompat
import java.util.*
import java.util.regex.Pattern

const val emStart = "<em class='highlight'>"
const val emEnd = "</em>"
const val fontStart = "<font color='red'>"
const val fontEnd = "</font>"

// 多关键词高亮
fun CharSequence?.makeTextHighlightForMultiKeys(key: String): CharSequence {
    if (this.isNullOrEmpty()) return ""
    val keys = key.trim().toLowerCase(Locale.getDefault()).replaceAllEmptyToOne().split(" ").toSet()

    var result: CharSequence = this
    keys.forEach {
        result = result.appendHtmlTags(it)
    }
    return HtmlCompat.fromHtml(result.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
}


// 搜索到的标题文本标红处理,返回的文本带有<em>标签
fun String?.toSearchTitleColorString(): CharSequence {
    return if (this.isNullOrEmpty()) "" else HtmlCompat.fromHtml(
        if (this.contains(emStart)) {
            this.replace(emStart, fontStart + emStart)
                .replace(emEnd, emEnd + fontEnd)
        } else {
            this
        },
        HtmlCompat.FROM_HTML_MODE_LEGACY
    )
}


fun CharSequence?.appendHtmlTags(key: String): CharSequence {
    if (this.isNullOrEmpty()) return ""
    if (!this.contains(key, true)) return this
    // 解析出整个字符串中所有包含key的位置
    val textArr: MutableList<Pair<String, String>> = mutableListOf()
    var searchIndex = 0
    while (searchIndex < this.length) {
        val index = this.indexOf(key, searchIndex, true)
        if (index != -1) {
            // 能匹到
            val keyword = this.substring(index, index + key.length) // 和key一样，只是大小写不一定一样
            val text = this.substring(searchIndex, index + key.length)
            searchIndex = index + key.length
            textArr.add(text to keyword)
        } else {
            if (searchIndex != length) {
                // 还有字符串
                textArr.add(substring(searchIndex) to key)
            }
            break
        }
    }

    val builder = StringBuilder()

    textArr.forEach {
        builder.append(
            if (!it.first.contains(it.second, true)) {
                it.first
            } else
                it.first.replace(
                    it.second,
                    fontStart + emStart + it.second + emEnd + fontEnd
                )
        )
    }
    return builder.toString()
}

// 替换所有空格为一个空格
fun String?.replaceAllEmptyToOne(): String {
    if (this.isNullOrEmpty()) return ""
    val pattern = Pattern.compile("\\s+")
    val matcher = pattern.matcher(this)
    return matcher.replaceAll(" ")
}