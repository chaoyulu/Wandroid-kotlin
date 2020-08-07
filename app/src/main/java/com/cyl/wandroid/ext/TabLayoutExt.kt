package com.cyl.wandroid.ext

import android.content.Context
import com.cyl.wandroid.tools.getScreenWidth
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.MODE_FIXED
import com.google.android.material.tabs.TabLayout.MODE_SCROLLABLE

fun TabLayout.setAdaptionTabMode(mContext: Context) {
    val tabWidth = getTabWidth(this)
    val screenWidth = getScreenWidth(mContext)
    tabMode = if (tabWidth >= screenWidth) {
        MODE_SCROLLABLE
    } else {
        MODE_FIXED
    }
}

private fun getTabWidth(tabLayout: TabLayout): Int {
    var totalWidth = 0
    val count = tabLayout.tabCount
    for (i in 0 until count) {
        val child = tabLayout.getTabAt(i) as TabLayout.Tab
        totalWidth += child.view.width
    }
    return totalWidth
}

fun TabLayout.addOnTabChangeListener(
    selected: (tab: TabLayout.Tab?) -> Unit,
    reselected: (tab: TabLayout.Tab?) -> Unit
) {
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
            reselected(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            selected(tab)
        }
    })
}