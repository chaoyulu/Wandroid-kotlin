package com.cyl.wandroid.tools

import android.graphics.Paint
import android.graphics.Rect

fun getTextWidth(paint: Paint, text: String): Float {
    val rect = Rect()
    paint.getTextBounds(text, 0, text.length, rect)
    return rect.height().toFloat()
}

fun getTextHeight(paint: Paint, text: String): Float {
    val rect = Rect()
    paint.getTextBounds(text, 0, text.length, rect)
    return rect.width().toFloat()
}