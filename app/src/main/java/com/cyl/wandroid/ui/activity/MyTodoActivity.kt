package com.cyl.wandroid.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseActivity
import com.cyl.wandroid.ext.addOnPageChangedListener
import com.cyl.wandroid.ext.addOnTabChangeListener
import com.cyl.wandroid.tools.makeStatusBarTransparent
import com.cyl.wandroid.tools.start
import com.cyl.wandroid.ui.adapter.ViewPagerAdapter
import com.cyl.wandroid.ui.fragment.MyTodoFragment
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_my_todo.*
import kotlinx.android.synthetic.main.layout_tab.*

class MyTodoActivity : BaseActivity() {
    private lateinit var fragments: List<Fragment>

    override fun initData() {
    }

    override fun initView() {
        loadFragments()
        setViewPagerTabLayout()
        makeStatusBarTransparent(this)
        toolbar.setBackgroundColor(Color.TRANSPARENT)
        toolbarMarginTop()
        setContainerBackground(0)
    }

    private fun toolbarMarginTop() {
        val params = toolbar.layoutParams as LinearLayout.LayoutParams
        params.topMargin = ImmersionBar.getStatusBarHeight(this)
        toolbar.layoutParams = params
    }

    override fun getLayoutRes() = R.layout.activity_my_todo

    private fun setViewPagerTabLayout() {
        val titles = listOf(
            resources.getString(R.string.to_do),
            resources.getString(R.string.done)
        )
        val adapter = ViewPagerAdapter(supportFragmentManager, fragments, titles)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = fragments.size
        titles.forEach {
            tabLayout.addTab(tabLayout.newTab().setText(it))
        }
        viewPager.addOnPageChangedListener {
            tabLayout.getTabAt(it)?.select()
            setContainerBackground(it)
        }

        tabLayout.addOnTabChangeListener(selected = {
            it?.let {
                viewPager.setCurrentItem(it.position, true)
                setContainerBackground(it.position)
            }
        }, reselected = {})

        tabLayout.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun setContainerBackground(position: Int) {
        llTodoContainer.setBackgroundColor(
            if (position == 0) ContextCompat.getColor(
                this,
                R.color.solid_orange
            ) else ContextCompat.getColor(
                this,
                R.color.solid_green
            )
        )
    }

    private fun loadFragments() {
        fragments = listOf(
            MyTodoFragment().apply {
                arguments =
                    Bundle().apply { putInt(MyTodoFragment.STATUS, MyTodoFragment.STATUS_TO_DO) }
            },
            MyTodoFragment().apply {
                arguments =
                    Bundle().apply { putInt(MyTodoFragment.STATUS, MyTodoFragment.STATUS_DONE) }
            }
        )
    }

    override fun onRightIconClick() {
        super.onRightIconClick()
        start(this, AddOrUpdateTodoActivity::class.java, Bundle().apply {
            putInt(AddOrUpdateTodoActivity.ACTION, AddOrUpdateTodoActivity.ACTION_ADD)
        }, needLogin = true)
    }
}