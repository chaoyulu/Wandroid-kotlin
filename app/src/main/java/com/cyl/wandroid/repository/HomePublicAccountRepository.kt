package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.CommonArticleData
import com.cyl.wandroid.http.bean.PublicAccountBean

class HomePublicAccountRepository {
    // 获取首页公众号列表
    suspend fun getPublicAccountList(): List<PublicAccountBean> {
        return ApiEngine.getApiService().getPublicAccountList().getApiData()
    }
}