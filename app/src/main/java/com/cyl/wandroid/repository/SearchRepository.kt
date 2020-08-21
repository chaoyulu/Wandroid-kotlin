package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.CommonArticleData
import com.cyl.wandroid.http.bean.HotKeyBean

class SearchRepository {
    suspend fun search(page: Int, k: String): CommonArticleData<ArticleBean> {
        return ApiEngine.getApiService().search(page, k).getApiData()
    }

    suspend fun getHotKey(): List<HotKeyBean> {
        return ApiEngine.getApiService().getHotKey().getApiData()
    }
}