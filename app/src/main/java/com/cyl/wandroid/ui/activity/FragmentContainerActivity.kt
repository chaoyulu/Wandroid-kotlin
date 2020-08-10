package com.cyl.wandroid.ui.activity

import androidx.fragment.app.Fragment
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseActivity

abstract class FragmentContainerActivity<F : Fragment> : BaseActivity() {
    override fun initData() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, createFragment())
            .commit()
    }

    abstract fun createFragment(): F

    override
    fun initView() {
    }

    override fun getLayoutRes() = R.layout.activity_fragment_container
}