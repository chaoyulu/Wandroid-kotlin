package com.cyl.wandroid.ext

import android.content.Context
import androidx.core.content.ContextCompat

fun Int.toColor(context: Context) = ContextCompat.getColor(context, this)