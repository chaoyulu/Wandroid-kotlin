package com.cyl.wandroid.ext

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View

// 设置View圆角
fun View.setCircularCorner(color: Int = Color.WHITE, cornerRadius: Float = 20f) {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.setColor(color)
    gradientDrawable.cornerRadius = cornerRadius
    background = gradientDrawable
}