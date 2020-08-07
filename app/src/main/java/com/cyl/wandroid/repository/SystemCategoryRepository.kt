package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.*

class SystemCategoryRepository {
    // 获取体系分类
    suspend fun getSystemCategory(): List<SystemCategoryBean> {
        return ApiEngine.getApiService().getSystemCategory().getApiData()
    }
}