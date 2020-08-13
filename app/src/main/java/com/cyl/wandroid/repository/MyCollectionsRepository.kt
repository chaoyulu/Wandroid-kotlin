package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.CommonArticleData

class MyCollectionsRepository {
    suspend fun getMyCollections(page: Int): CommonArticleData<ArticleBean> {
        return ApiEngine.getApiService().getMyCollections(page).getApiData()
    }
}