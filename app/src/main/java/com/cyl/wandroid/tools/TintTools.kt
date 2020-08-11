package com.cyl.wandroid.tools

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.widget.ImageView

/**
 * 给ImageView着色
 */
fun imgTint(imageView: ImageView, color: Int) {
    val colorStateList: ColorStateList =
        ColorStateList.valueOf(color)
    imageView.imageTintList = colorStateList
    imageView.imageTintMode = PorterDuff.Mode.SRC_IN
}