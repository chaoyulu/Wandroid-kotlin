package com.cyl.wandroid.ext

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.cyl.wandroid.R

fun ImageView.loadImage(url: String) {
    Glide.with(context).load(url).apply(getRequestOption()).into(this)
}

fun ImageView.loadImage(url: String, width: Int, height: Int) {
    Glide.with(context).load(url).apply(getRequestOption(width, height)).into(this)
}

fun getRequestOption() =
    RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
        .diskCacheStrategy(DiskCacheStrategy.ALL)

fun getRequestOption(width: Int, height: Int) =
    RequestOptions().centerCrop().override(width, height).placeholder(R.mipmap.ic_launcher)
        .error(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL)