package com.cyl.wandroid.http.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProjectCategoryBean(
    val children: List<ProjectCategoryBean>,
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int
) : Parcelable