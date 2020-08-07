package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.CommonArticleData

class HomeNewestShareRepository {
    // 获取首页最新分享
    suspend fun getHomeNewestShare(page: Int): CommonArticleData<ArticleBean> {
        return ApiEngine.getApiService().getHomeNewestShare(page).getApiData()
    }
}