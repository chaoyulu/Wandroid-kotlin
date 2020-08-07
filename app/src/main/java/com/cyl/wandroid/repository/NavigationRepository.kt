package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.NavigationBean

class NavigationRepository {
    suspend fun getNavigation(): List<NavigationBean> {
        return ApiEngine.getApiService().getNavigation().getApiData()
    }
}