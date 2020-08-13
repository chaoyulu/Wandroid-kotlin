package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.ShareBean
import com.cyl.wandroid.http.bean.UserBean
import com.cyl.wandroid.sp.UserSpHelper

class AddSharedRepository {
    suspend fun addShared(title: String, link: String): ShareBean {
        return ApiEngine.getApiService().addShare(title, link).getApiData()
    }

    fun getUserInfo(): UserBean? {
        return UserSpHelper.newHelper().getUserInfo()
    }
}