package com.cyl.wandroid.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.jetbrains.annotations.NotNull

class ViewPager2Adapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private lateinit var fragments: List<Fragment>

    constructor(
        fragment: Fragment, fragments: List<Fragment>
    ) : this(fragment.requireActivity()) {
        this.fragments = fragments
    }

    constructor(fragmentActivity: FragmentActivity, fragments: List<Fragment>) : this(
        fragmentActivity
    ) {
        this.fragments = fragments
    }


    override fun createFragment(position: Int) = fragments[position]

    override fun getItemCount() = fragments.size
}