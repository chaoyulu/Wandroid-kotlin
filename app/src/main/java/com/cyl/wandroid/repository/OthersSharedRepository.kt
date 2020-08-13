package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.ShareBean

class OthersSharedRepository {
    suspend fun getOthersShared(id: Int, page: Int): ShareBean {
        return ApiEngine.getApiService().getOthersShared(id, page).getApiData()
    }
}