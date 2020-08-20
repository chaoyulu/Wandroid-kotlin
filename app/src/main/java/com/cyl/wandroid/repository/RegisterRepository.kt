package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.UserBean

class RegisterRepository {
    suspend fun register(username: String, password: String, rePassword: String): UserBean {
        return ApiEngine.getApiService().register(username, password, rePassword).getApiData()
    }
}