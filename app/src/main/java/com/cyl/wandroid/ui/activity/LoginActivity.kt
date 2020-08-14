package com.cyl.wandroid.ui.activity

import androidx.lifecycle.Observer
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseViewModelActivity
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.REFRESH_LOGIN_SUCCESS
import com.cyl.wandroid.tools.makeStatusBarTransparent
import com.cyl.wandroid.tools.showError
import com.cyl.wandroid.tools.start
import com.cyl.wandroid.ui.dialog.LoadingDialog
import com.cyl.wandroid.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseViewModelActivity<LoginViewModel>() {

    override fun getViewModelClass() = LoginViewModel::class.java
    private var loadingDialog: LoadingDialog? = null

    override fun initData() {
        initClick()
    }

    private fun initClick() {
        stvRegister.setOnClickListener {
            start(this, RegisterActivity::class.java)
        }

        stvLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        if (username.isEmpty() || password.isEmpty()) {
            showError(R.string.lose_username_password)
            return
        }
        if (loadingDialog == null) loadingDialog = LoadingDialog(this)
        loadingDialog?.setDesc(R.string.logging)
        mViewModel.login(username, password)
    }

    override fun initView() {
        makeStatusBarTransparent(this)
    }

    override fun getLayoutRes() = R.layout.activity_login

    override fun observe() {
        mViewModel.apply {
            userBeanLiveData.observe(this@LoginActivity, Observer {
                // 登录成功
                Bus.post(REFRESH_LOGIN_SUCCESS, it)
                finish()
            })

            loginStatusLiveData.observe(this@LoginActivity, Observer {
                if (it) loadingDialog?.show() else loadingDialog?.dismiss()
            })
        }
    }
}