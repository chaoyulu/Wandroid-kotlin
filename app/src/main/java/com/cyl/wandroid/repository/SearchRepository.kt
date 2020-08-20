package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.CommonArticleData

class SearchRepository {
    suspend fun search(page: Int, k: String): CommonArticleData<ArticleBean> {
        return ApiEngine.getApiService().search(page, k).getApiData()
    }
}