package com.cyl.wandroid.tools

import android.app.Activity
import com.gyf.immersionbar.ImmersionBar

/**
 * 使Activity状态栏透明，字体变白色
 */
fun makeStatusBarTransparent(activity: Activity) {
    ImmersionBar.with(activity).fitsSystemWindows(false).statusBarColor(android.R.color.transparent)
        .statusBarDarkFont(false).init()
}