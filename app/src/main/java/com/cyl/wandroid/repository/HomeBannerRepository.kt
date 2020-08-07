package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.HomeBannerBean

class HomeBannerRepository {
    suspend fun getHomeBanner(): List<HomeBannerBean> {
        return ApiEngine.getApiService().getHomeBanner().getApiData()
    }
}