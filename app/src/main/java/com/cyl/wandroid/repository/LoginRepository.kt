package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.UserBean

class LoginRepository {
    suspend fun login(username: String, password: String): UserBean {
        return ApiEngine.getApiService().login(username, password).getApiData()
    }
}