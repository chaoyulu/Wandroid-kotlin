package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseViewModel
import com.cyl.wandroid.http.bean.UserBean
import com.cyl.wandroid.repository.LoginRepository

class LoginViewModel : BaseViewModel() {
    private val loginRepository by lazy { LoginRepository() }
    val userBeanLiveData = MutableLiveData<UserBean>()

    fun login(username: String, password: String) {
        launch(block = {
            userBeanLiveData.value = loginRepository.login(username, password)
        })
    }
}