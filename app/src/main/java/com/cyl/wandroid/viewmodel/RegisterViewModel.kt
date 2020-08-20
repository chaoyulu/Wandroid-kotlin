package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseViewModel
import com.cyl.wandroid.http.bean.UserBean
import com.cyl.wandroid.repository.RegisterRepository

class RegisterViewModel : BaseViewModel() {
    private val registerRepository by lazy { RegisterRepository() }
    val userBeanLiveData = MutableLiveData<UserBean>()
    val registerStatusLiveData = MutableLiveData<Boolean>()

    fun register(username: String, password: String, rePassword: String) {
        launch(block = {
            registerStatusLiveData.value = true
            userBeanLiveData.value = registerRepository.register(username, password, rePassword)
            registerStatusLiveData.value = false
        }, error = { registerStatusLiveData.value = false })
    }
}