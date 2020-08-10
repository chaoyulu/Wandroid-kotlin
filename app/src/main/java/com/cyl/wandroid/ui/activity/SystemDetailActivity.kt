package com.cyl.wandroid.ui.activity

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseActivity
import com.cyl.wandroid.ext.addOnPageChangedListener
import com.cyl.wandroid.ext.addOnTabChangeListener
import com.cyl.wandroid.ext.setAdaptionTabMode
import com.cyl.wandroid.http.bean.SystemCategoryBean
import com.cyl.wandroid.ui.adapter.ViewPagerAdapter
import com.cyl.wandroid.ui.fragment.SystemSubFragment
import kotlinx.android.synthetic.main.activity_system_detail.*
import kotlinx.android.synthetic.main.layout_tab.*

class SystemDetailActivity : BaseActivity() {
    private var tagPosition: Int = 0
    private var category: SystemCategoryBean? = null
    private var titles: MutableList<String> = mutableListOf()
    private var fragments: MutableList<Fragment> = mutableListOf()

    override fun initData() {
        intent?.extras?.apply {
            tagPosition = getInt("tagPosition")
            category = getParcelable("category")
        }
        loadFragments()
        initTabLayout()
    }

    override fun initView() {
    }

    override fun getLayoutRes() = R.layout.activity_system_detail

    private fun loadFragments() {
        category?.children?.forEach {
            titles.add(it.name)
            fragments.add(SystemSubFragment.newFragment(it))
            tabLayout.addTab(tabLayout.newTab().setText(it.name))
        }
        tabLayout.setupWithViewPager(viewPager)
        if (tabLayout.tabCount <= 1) {
            tabLayout.isVisible = false
        } else {
            tabLayout.post { tabLayout.setAdaptionTabMode(this) }
        }
    }

    private fun initTabLayout() {
        val adapter = ViewPagerAdapter(supportFragmentManager, fragments, titles)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = fragments.size
        viewPager.setCurrentItem(tagPosition, true)
        tabLayout.getTabAt(tagPosition)?.select()
        setCenterText("${category?.name} - ${titles[tagPosition]}")

        tabLayout.addOnTabChangeListener(
            selected = {
                it?.let {
                    setCenterText("${category?.name} - ${it.text.toString()}")
                    viewPager.setCurrentItem(it.position, true)
                }
            },
            reselected = {})

        viewPager.addOnPageChangedListener {
            setCenterText("${category?.name} - ${titles[it]}")
            tabLayout.getTabAt(it)?.select()
        }
    }
}
