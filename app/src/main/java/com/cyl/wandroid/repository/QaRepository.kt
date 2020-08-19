package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.CommonArticleData

class QaRepository {
    suspend fun getQA(page: Int): CommonArticleData<ArticleBean> {
        return ApiEngine.getApiService().getQA(page).getApiData()
    }
}