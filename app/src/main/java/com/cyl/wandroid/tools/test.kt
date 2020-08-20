package com.cyl.wandroid.tools

const val emStart = "<em class='highlight'>"
const val emEnd = "</em>"
const val fontStart = "<font color=\"red\">"
const val fontEnd = "</font>"
fun main() {

    val author = "coder"
    println(author.toSearchAuthorColorString("Coder"))
}

fun String?.toSearchAuthorColorString(key: String): CharSequence {
    if (this.isNullOrEmpty()) return ""
    if (!this.contains(key, true)) return this
    // 解析出整个字符串中所有包含key的位置
    // coder
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
    return builder
}