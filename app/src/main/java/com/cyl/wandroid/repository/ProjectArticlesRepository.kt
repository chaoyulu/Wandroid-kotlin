package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.CommonArticleData
import com.cyl.wandroid.http.bean.ProjectCategoryBean
import com.cyl.wandroid.http.bean.PublicAccountBean

class ProjectArticlesRepository {
    // 获取项目分类
    suspend fun getProjectArticles(page: Int, cid: Int): CommonArticleData<ArticleBean> {
        return ApiEngine.getApiService().getProjectArticles(page, cid).getApiData()
    }
}