package com.cyl.wandroid.tools

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.cyl.wandroid.sp.UserSpHelper
import com.cyl.wandroid.ui.activity.LoginActivity

fun start(
    context: Context,
    clazz: Class<out Activity>,
    bundle: Bundle? = null,
    needLogin: Boolean = false, // 是否需要登录
    needResult: Boolean = false, // 是否调用startActivityForResult
    requestCode: Int = 0 // 请求码
) {
    if (!needLogin) {
        // 无需登录
        startActivity(context, clazz, bundle, needResult, requestCode)
    } else {
        // 需要登录，判断是否登录
        if (UserSpHelper.newHelper().isLogin()) {
            startActivity(context, clazz, bundle, needResult, requestCode)
        } else {
            start(context, LoginActivity::class.java)
        }
    }
}

private fun startActivity(
    context: Context,
    clazz: Class<out Activity>,
    bundle: Bundle? = null,
    needResult: Boolean,
    requestCode: Int
) {
    val intent = Intent(context, clazz)
    if (bundle != null) intent.putExtras(bundle)
    if (needResult) {
        if (context is Activity) {
            context.startActivityForResult(intent, requestCode)
        } else {
            // 如果不是通过Activity启动，就不需要返回值
            context.startActivity(intent)
        }
    } else {
        context.startActivity(intent)
    }
}