package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.ShareBean

class MySharedRepository {
    suspend fun getMyShared(page: Int): ShareBean {
        return ApiEngine.getApiService().getMyShared(page).getApiData()
    }

    suspend fun deleteMyShare(id: Int) {
        ApiEngine.getApiService().deleteMyShare(id).getApiData()
    }
}