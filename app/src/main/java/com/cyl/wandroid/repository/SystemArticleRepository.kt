package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.CommonArticleData

class SystemArticleRepository {
    suspend fun getSystemArticles(page: Int, cid: Int): CommonArticleData<ArticleBean> {
        return ApiEngine.getApiService().getSystemArticles(page, cid).getApiData()
    }
}