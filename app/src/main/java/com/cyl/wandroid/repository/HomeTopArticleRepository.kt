package com.cyl.wandroid.repository

import android.util.Log
import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.CommonArticleData
import com.cyl.wandroid.http.bean.HomeBannerBean

class HomeTopArticleRepository {
    // 获取首页置顶文章
    suspend fun getHomeTopArticles(): List<ArticleBean> {
        return ApiEngine.getApiService().getHomeTopArticles().getApiData()
    }

    // 获取首页文章，首页文章添加在置顶文章底部
    suspend fun getHomePageArticles(page: Int): CommonArticleData<ArticleBean> {
        return ApiEngine.getApiService().getHomePageArticles(page).getApiData()
    }

    suspend fun getHomeBanner(): List<HomeBannerBean> {
        Log.e("CurrentThreadName ", Thread.currentThread().name)
        return ApiEngine.getApiService().getHomeBanner().getApiData()
    }
}
