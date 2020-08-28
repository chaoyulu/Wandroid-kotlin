package com.cyl.wandroid.ui.activity

import androidx.lifecycle.Observer
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseViewModelActivity
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.HOME_TODO_STATUS_CHANGED
import com.cyl.wandroid.common.bus.MARK_COLLECT_LOGIN_SUCCESS
import com.cyl.wandroid.common.bus.REFRESH_LOGIN_SUCCESS
import com.cyl.wandroid.ext.hideSoftInput
import com.cyl.wandroid.tools.makeStatusBarTransparent
import com.cyl.wandroid.tools.showError
import com.cyl.wandroid.tools.start
import com.cyl.wandroid.ui.dialog.LoadingDialog
import com.cyl.wandroid.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseViewModelActivity<RegisterViewModel>() {
    private var loadingDialog: LoadingDialog? = null
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
            start(this, LoginActivity::class.java)
            finish()
        }

        stvRegister.setOnClickListener {
            it.hideSoftInput()
            register()
        }
    }

    override fun observe() {
        mViewModel.apply {
            userBeanLiveData.observe(this@RegisterActivity, Observer {
                Bus.post(REFRESH_LOGIN_SUCCESS, it)
                Bus.post(MARK_COLLECT_LOGIN_SUCCESS, it)
                Bus.post(HOME_TODO_STATUS_CHANGED, true)
                start(this@RegisterActivity, MainActivity::class.java)
                finish()
            })
            registerStatusLiveData.observe(this@RegisterActivity, Observer {
                if (it) loadingDialog?.show() else loadingDialog?.dismiss()
            })
        }
    }

    private fun register() {
        val username = etUsername.text.toString()
        if (username.isEmpty()) {
            showError(R.string.input_username)
            return
        }
        val password = etPassword.text.toString()
        if (password.isEmpty()) {
            showError(R.string.input_password)
            return
        }
        val rePassword = etRePassword.text.toString()
        if (rePassword.isEmpty()) {
            showError(R.string.password_again)
            return
        }
        if (loadingDialog == null) loadingDialog = LoadingDialog(this)
        mViewModel.register(username, password, rePassword)
    }
}