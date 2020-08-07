package com.cyl.wandroid.ui.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseViewModelFragment
import com.cyl.wandroid.ext.addOnPageChangedListener
import com.cyl.wandroid.ext.addOnTabChangeListener
import com.cyl.wandroid.ui.adapter.ViewPagerAdapter
import com.cyl.wandroid.viewmodel.ProjectCategoryViewModel
import kotlinx.android.synthetic.main.fragment_project.*
import kotlinx.android.synthetic.main.layout_tab.*

/**
 * 项目
 */
class ProjectFragment : BaseViewModelFragment<ProjectCategoryViewModel>() {
    private var fragments: MutableList<Fragment> = mutableListOf()
    private var titles: MutableList<String> = mutableListOf()

    override fun getLayoutRes() = R.layout.fragment_project
    override fun lazyInitData() {
        mViewModel.getProjectCategory()
    }

    override fun initView() {
    }

    private fun setViewPagerTabLayout() {
        val adapter = ViewPagerAdapter(childFragmentManager, fragments, titles)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = fragments.size

        viewPager.addOnPageChangedListener {
            tabLayout.getTabAt(it)?.select()
        }

        tabLayout.addOnTabChangeListener(selected = {
            it?.let { viewPager.setCurrentItem(it.position, true) }
        }, reselected = {})
    }

    override fun observe() {
        mViewModel.apply {
            categories.observe(viewLifecycleOwner, Observer { it ->
                it.forEach {
                    fragments.add(ProjectSubFragment.newFragment(it))
                    titles.add(it.name)
                    tabLayout.addTab(tabLayout.newTab().setText(it.name))
                }
                setViewPagerTabLayout()
            })
        }
    }

    override fun getViewModelClass() = ProjectCategoryViewModel::class.java
}
