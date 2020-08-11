package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseViewModel
import com.cyl.wandroid.http.bean.UserBean
import com.cyl.wandroid.repository.RegisterRepository

class RegisterViewModel : BaseViewModel() {
    private val registerRepository by lazy { RegisterRepository() }
    val userBeanLiveData = MutableLiveData<UserBean>()

    fun register(username: String, password: String, repassword: String) {
        launch(block = {
            userBeanLiveData.value = registerRepository.register(username, password, repassword)
        })
    }
}