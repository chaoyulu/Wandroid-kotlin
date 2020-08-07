package com.cyl.wandroid.ext

import androidx.viewpager.widget.ViewPager

fun ViewPager.addOnPageChangedListener(
    selected: (position: Int) -> Unit
) {
    addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            selected(position)
        }
    })
}