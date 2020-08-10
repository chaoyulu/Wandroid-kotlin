package com.cyl.wandroid.ui.widget

import android.content.Context
import androidx.recyclerview.widget.LinearSmoothScroller

/**
 * 将RecyclerView定位到某个item到屏幕顶部
 */
class SmoothTopScroller(context: Context) : LinearSmoothScroller(context) {
    override fun getHorizontalSnapPreference(): Int {
        return SNAP_TO_START
    }

    override fun getVerticalSnapPreference(): Int {
        return SNAP_TO_START
    }
}