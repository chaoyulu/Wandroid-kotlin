package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.CommonArticleData
import com.cyl.wandroid.http.bean.PointRankBean
import com.cyl.wandroid.http.bean.PointsListBean

class MyPointsRepository {
    suspend fun getMyPointsInfo(): PointRankBean {
        return ApiEngine.getApiService().getMyPointsInfo().getApiData()
    }

    suspend fun getMyPointsList(page: Int): CommonArticleData<PointsListBean> {
        return ApiEngine.getApiService().getMyPointsList(page).getApiData()
    }
}