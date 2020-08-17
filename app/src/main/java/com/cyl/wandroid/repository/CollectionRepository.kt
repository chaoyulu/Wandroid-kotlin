package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine

class CollectionRepository {
    // 收藏
    suspend fun collect(id: Int) {
        ApiEngine.getApiService().collectArticle(id)
    }

    // 文章列表取消
    suspend fun cancelCollectFromArticleList(id: Int) {
        ApiEngine.getApiService().cancelCollectFromArticleList(id)
    }

    // 收藏列表取消
    suspend fun cancelCollectFromCollectionList(id: Int, originId: Int) {
        ApiEngine.getApiService().cancelCollectFromCollectionList(id, originId)
    }
}