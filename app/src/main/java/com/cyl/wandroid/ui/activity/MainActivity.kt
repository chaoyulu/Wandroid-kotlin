package com.cyl.wandroid.ui.activity

import androidx.fragment.app.Fragment
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseActivity
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.JUMP_TO_PROJECT_FRAGMENT
import com.cyl.wandroid.ui.fragment.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private lateinit var fragments: Map<Int, Fragment>
    private var lastItemId = R.id.home // 上一次显示的id

    override fun getLayoutRes() = R.layout.activity_main

    override fun initData() {
        loadFragments()

        showFragment(R.id.home)
        lastItemId = R.id.home
        bottomNavigationView.selectedItemId = R.id.home

        bottomNavigationView.setOnNavigationItemSelectedListener {
            showFragment(it.itemId)
            lastItemId = it.itemId
            true
        }

        busObserve()
    }

    // LiveEventBus订阅
    private fun busObserve() {
        Bus.observe<Int>(JUMP_TO_PROJECT_FRAGMENT, this, observer = {
            showFragment(it)
            bottomNavigationView.menu.getItem(1).isChecked = true
        })
    }

    override fun initView() {
    }

    private fun loadFragments() {
        fragments = mapOf(
            R.id.home to newFragment(HomeFragment::class.java),
            R.id.project to newFragment(ProjectFragment::class.java),
            R.id.system to newFragment(SystemFragment::class.java),
            R.id.navigation to newFragment(NavigationFragment::class.java),
            R.id.mine to newFragment(MineFragment::class.java)
        )
    }

    // out==java中的extends，可接受Fragment的子类泛型
    private fun newFragment(clazz: Class<out Fragment>): Fragment {
        // 根据class找出Fragment，若存在直接返回，否则创建一个新的再返回
        var fragment = supportFragmentManager.fragments.find { clazz == it.javaClass }
        if (fragment == null) {
            fragment = when (clazz) {
                HomeFragment::class.java -> HomeFragment()
                ProjectFragment::class.java -> ProjectFragment()
                SystemFragment::class.java -> SystemFragment()
                NavigationFragment::class.java -> NavigationFragment()
                MineFragment::class.java -> MineFragment()
                else -> throw IllegalArgumentException("create fragment error")
            }
        }
        return fragment
    }

    private fun showFragment(id: Int) {
        val currentFragment = supportFragmentManager.fragments.find {
            it.isVisible && it in fragments.values
        }

        val targetFragment = fragments.entries.find { it.key == id }?.value
        supportFragmentManager.beginTransaction().apply {
            currentFragment?.let { if (it.isVisible) hide(it) }
            targetFragment?.let {
                if (it.isAdded) show(it) else add(R.id.frameLayout, it)
            }
        }.commit()
    }
}
