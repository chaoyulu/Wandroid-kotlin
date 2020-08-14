package com.cyl.wandroid.tools

import android.content.Context
import com.cyl.wandroid.sp.UserSpHelper
import com.cyl.wandroid.ui.activity.LoginActivity

/**
 * 判断是否登录，未登录就跳转到登录界面，已登录就继续执行action里面的代码
 */
fun checkLoginThenAction(context: Context, action: (() -> Unit)? = null): Boolean {
    return if (UserSpHelper.newHelper().isLogin()) {
        action?.invoke()
        true
    } else {
        start(context, LoginActivity::class.java)
        false
    }
}