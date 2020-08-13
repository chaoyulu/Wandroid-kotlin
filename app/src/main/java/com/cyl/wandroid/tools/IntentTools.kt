package com.cyl.wandroid.tools

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.cyl.wandroid.sp.UserSpHelper
import com.cyl.wandroid.ui.activity.LoginActivity

object IntentTools {
    fun start(
        context: Context,
        clazz: Class<out Activity>,
        bundle: Bundle? = null,
        needLogin: Boolean = false // 是否需要登录
    ) {
        if (!needLogin) {
            // 无需登录
            val intent = Intent(context, clazz)
            if (bundle != null) intent.putExtras(bundle)
            context.startActivity(intent)
        } else {
            // 需要登录，判断是否登录
            if (UserSpHelper.newHelper().isLogin()) {
                val intent = Intent(context, clazz)
                if (bundle != null) intent.putExtras(bundle)
                context.startActivity(intent)
            } else {
                start(context, LoginActivity::class.java)
            }
        }
    }
}