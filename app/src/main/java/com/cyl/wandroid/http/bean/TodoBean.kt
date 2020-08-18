package com.cyl.wandroid.http.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TodoBean(
    val completeDate: Long,
    val completeDateStr: String,
    val content: String,
    val date: Long,
    val dateStr: String,
    val id: Int,
    val priority: Int,
    val status: Int,
    val title: String,
    val type: Int,
    val userId: Int
) : Parcelable