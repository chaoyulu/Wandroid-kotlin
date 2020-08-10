package com.cyl.wandroid.tools

import android.view.Gravity
import android.widget.Toast
import com.cyl.wandroid.R
import com.cyl.wandroid.base.App
import es.dmoral.toasty.Toasty

private const val TYPE_NORMAL = 0
private const val TYPE_ERROR = 1

private fun customToast(
    text: Int,
    textSize: Int = 12,
    type: Int = TYPE_NORMAL,
    gravity: Int = Gravity.TOP,
    xOffset: Int = 0,
    yOffset: Int = 100,
    duration: Int = Toast.LENGTH_SHORT
) {
    customToast(App.app.getString(text), textSize, type, gravity, xOffset, yOffset, duration)
}

private fun customToast(
    text: String,
    textSize: Int = 12,
    type: Int = TYPE_NORMAL,
    gravity: Int = Gravity.TOP,
    xOffset: Int = 0,
    yOffset: Int = 100,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toasty.Config.getInstance().allowQueue(true).setTextSize(textSize).apply()
    val toast: Toast = if (type == TYPE_NORMAL) {
        Toasty.normal(App.app, text)
    } else {
        Toasty.error(App.app, text)
    }
    val view = toast.view
    view.setBackgroundResource(if (type == TYPE_NORMAL) R.drawable.shape_toast_normal else R.drawable.shape_toast_error)
    toast.apply {
        this.view = view
        this.duration = duration
        setGravity(gravity, xOffset, yOffset)
        show()
    }
}

fun showNormal(msg: Int) {
    customToast(text = msg)
}

fun showNormal(msg: String) {
    customToast(text = msg)
}

fun showNormalLong(msg: Int) {
    customToast(msg, duration = Toast.LENGTH_LONG)
}

fun showNormalLong(msg: String) {
    customToast(msg, duration = Toast.LENGTH_LONG)
}

fun showNormalCenter(msg: Int) {
    customToast(msg, gravity = Gravity.CENTER, yOffset = 0)
}

fun showNormalCenter(msg: String) {
    customToast(msg, gravity = Gravity.CENTER, yOffset = 0)
}

fun showError(msg: Int) {
    customToast(msg, type = TYPE_ERROR)
}

fun showError(msg: String) {
    customToast(msg, type = TYPE_ERROR)
}