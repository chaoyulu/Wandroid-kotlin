package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.CommonArticleData
import com.cyl.wandroid.http.bean.PointRankBean

class PointsRankRepository {
    suspend fun getPointsRank(page: Int): CommonArticleData<PointRankBean> {
        return ApiEngine.getApiService().getAllPointsRank(page).getApiData()
    }
}