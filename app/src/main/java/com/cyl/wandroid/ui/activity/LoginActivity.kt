package com.cyl.wandroid.ui.activity

import android.util.Log
import androidx.lifecycle.Observer
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseViewModelActivity
import com.cyl.wandroid.tools.showError
import com.cyl.wandroid.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseViewModelActivity<LoginViewModel>() {

    override fun getViewModelClass() = LoginViewModel::class.java

    override fun initData() {
        initClick()
    }

    private fun initClick() {
        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()
        if (username.isEmpty() || password.isEmpty()) {
            showError("用户名或密码未填写")
            return
        }

        mViewModel.login(username, password)
    }

    override fun initView() {
    }

    override fun getLayoutRes() = R.layout.activity_login

    override fun observe() {
        mViewModel.apply {
            userBeanLiveData.observe(this@LoginActivity, Observer {
                Log.e("========", it.nickname)
            })
        }
    }
}