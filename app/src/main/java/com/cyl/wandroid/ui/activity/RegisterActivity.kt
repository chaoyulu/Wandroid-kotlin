package com.cyl.wandroid.ui.activity

import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseViewModelActivity
import com.cyl.wandroid.tools.IntentTools
import com.cyl.wandroid.tools.makeStatusBarTransparent
import com.cyl.wandroid.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseViewModelActivity<RegisterViewModel>() {
    override fun observe() {
    }

    override fun getViewModelClass() = RegisterViewModel::class.java

    override fun initData() {
        initClick()
    }

    override fun initView() {
        makeStatusBarTransparent(this)
    }

    override fun getLayoutRes() = R.layout.activity_register

    private fun initClick() {
        stvLogin.setOnClickListener {
            IntentTools.start(this, LoginActivity::class.java)
            finish()
        }
    }
}