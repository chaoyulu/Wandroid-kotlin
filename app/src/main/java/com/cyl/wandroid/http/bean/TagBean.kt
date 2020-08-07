package com.cyl.wandroid.http.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TagBean(
    val name: String,
    val url: String
) : Parcelable