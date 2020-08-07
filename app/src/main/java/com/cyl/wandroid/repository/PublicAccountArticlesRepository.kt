package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.CommonArticleData
import com.cyl.wandroid.http.bean.PublicAccountBean

class PublicAccountArticlesRepository {
    // 获取首页公众号文章
    suspend fun getPublicAccountArticles(id: Int, page: Int): CommonArticleData<ArticleBean> {
        return ApiEngine.getApiService().getPublicAccountArticles(id, page).getApiData()
    }
}