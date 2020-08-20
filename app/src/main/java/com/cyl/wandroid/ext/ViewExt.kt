package com.cyl.wandroid.ext

import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager

// 设置View圆角
fun View.setCircularCorner(color: Int = Color.WHITE, cornerRadius: Float = 20f) {
    val gradientDrawable = GradientDrawable()
    gradientDrawable.setColor(color)
    gradientDrawable.cornerRadius = cornerRadius
    background = gradientDrawable
}

// 隐藏键盘
fun View.hideSoftInput() {
    val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}