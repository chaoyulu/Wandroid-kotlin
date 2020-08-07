package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.PublicAccountBean
import com.cyl.wandroid.repository.HomePublicAccountRepository
import com.cyl.wandroid.repository.PublicAccountArticlesRepository

class PublicAccountArticlesViewModel : BaseRecyclerViewModel() {
    private val publicAccountArticlesRepository by lazy { PublicAccountArticlesRepository() }
    private var page = 0

    val articles: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()

    fun refreshPublicAccountArticles(id: Int) {
        page = 0
        getPublicAccountArticles(id)
    }

    fun loadMorePublicAccountArticles(id: Int) {
        getPublicAccountArticles(id)
    }

    private fun getPublicAccountArticles(id: Int) {
        launch(block = {
            if (page == 0) {
                // 下拉刷新
                setRefreshStatus(true)
                val data = publicAccountArticlesRepository.getPublicAccountArticles(id, 0)
                articles.value = mutableListOf<ArticleBean>().apply { addAll(data.datas) }
                page = data.curPage + 1
                setRefreshStatus(false)
            } else {
                // 上拉加载更多
                setLoadMoreStart()
                val data = publicAccountArticlesRepository.getPublicAccountArticles(id, page)
                val list = articles.value ?: mutableListOf()
                list.addAll(data.datas)
                articles.value = list
                page = data.curPage + 1
                setLoadMoreFinishStatus(data.offset, data.total)
            }
        })
    }
}