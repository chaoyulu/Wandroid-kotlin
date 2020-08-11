package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseViewModel
import com.cyl.wandroid.http.bean.UserBean
import com.cyl.wandroid.repository.LoginRepository
import com.cyl.wandroid.sp.UserSpHelper

class LoginViewModel : BaseViewModel() {
    private val loginRepository by lazy { LoginRepository() }
    val userBeanLiveData = MutableLiveData<UserBean>()

    fun login(username: String, password: String) {
        launch(block = {
            val userBean = loginRepository.login(username, password)
            userBeanLiveData.value = userBean
            UserSpHelper.newHelper().saveUserInfo(userBean)
        })
    }
}