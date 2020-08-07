package com.cyl.wandroid.tools

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

object IntentTools {
    fun start(context: Context, clazz: Class<out Activity>, bundle: Bundle?) {
        val intent = Intent(context, clazz)
        if (bundle != null) intent.putExtras(bundle)
        context.startActivity(intent)
    }
}