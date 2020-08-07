package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.CommonArticleData

class HomeNewestProjectRepository {
    // 获取首页最新项目
    suspend fun getHomeNewestProject(page: Int): CommonArticleData<ArticleBean> {
        return ApiEngine.getApiService().getHomeNewestProjects(page).getApiData()
    }
}