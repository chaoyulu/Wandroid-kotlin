package com.cyl.wandroid.tools

import android.content.Context
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.cyl.wandroid.R
import com.cyl.wandroid.sp.UserSpHelper

fun setCollectionImgState(context: Context, ivCollection: ImageView, isCollect: Boolean) {
    ivCollection.setImageResource(R.mipmap.icon_collect)
    if (!UserSpHelper.newHelper().isLogin()) {
        imgTint(ivCollection, ContextCompat.getColor(context, R.color.grey_AAAAAA))
    } else {
        if (isCollect) {
            imgTint(ivCollection, ContextCompat.getColor(context, R.color.red_FF0000))
        } else {
            imgTint(ivCollection, ContextCompat.getColor(context, R.color.grey_AAAAAA))
        }
    }
}