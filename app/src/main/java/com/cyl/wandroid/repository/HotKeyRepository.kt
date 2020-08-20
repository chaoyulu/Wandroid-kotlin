package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.HotKeyBean

class HotKeyRepository {
    suspend fun getHotKey(): List<HotKeyBean> {
        return ApiEngine.getApiService().getHotKey().getApiData()
    }
}