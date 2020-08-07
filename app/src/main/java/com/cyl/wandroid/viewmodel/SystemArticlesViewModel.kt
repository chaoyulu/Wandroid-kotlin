package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.PublicAccountBean
import com.cyl.wandroid.repository.HomePublicAccountRepository
import com.cyl.wandroid.repository.PublicAccountArticlesRepository
import com.cyl.wandroid.repository.SystemArticleRepository

class SystemArticlesViewModel : BaseRecyclerViewModel() {
    private val systemArticleRepository by lazy { SystemArticleRepository() }
    private var page = 0
    private val pageStart = 0

    val articles: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()

    fun refreshSystemArticles(cid: Int) {
        page = pageStart
        getSystemArticles(cid)
    }

    fun loadMoreSystemArticles(cid: Int) {
        getSystemArticles(cid)
    }

    private fun getSystemArticles(cid: Int) {
        launch(block = {
            if (page == pageStart) {
                // 下拉刷新
                setRefreshStatus(true)
                val data = systemArticleRepository.getSystemArticles(pageStart, cid)
                articles.value = mutableListOf<ArticleBean>().apply { addAll(data.datas) }
                page = data.curPage
                setRefreshStatus(false)
            } else {
                // 上拉加载更多
                setLoadMoreStart()
                val data = systemArticleRepository.getSystemArticles(page, cid)
                val list = articles.value ?: mutableListOf()
                list.addAll(data.datas)
                articles.value = list
                page = data.curPage
                setLoadMoreFinishStatus(data.offset, data.total)
            }
        })
    }
}