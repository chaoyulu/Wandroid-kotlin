package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.repository.ProjectArticlesRepository

class ProjectArticlesViewModel : CollectViewModel() {
    private val projectArticlesRepository by lazy { ProjectArticlesRepository() }
    private var page = 1
    private val pageStart = 1 // 从第一页开始

    val articles: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()

    fun refreshProjectArticles(cid: Int) {
        page = pageStart
        getProjectArticles(cid)
    }

    fun loadMoreProjectArticles(cid: Int) {
        getProjectArticles(cid)
    }

    private fun getProjectArticles(cid: Int) {
        launch(block = {
            if (page == pageStart) {
                // 下拉刷新
                setRefreshStatus(true)
                val data = projectArticlesRepository.getProjectArticles(pageStart, cid)
                articles.value = mutableListOf<ArticleBean>().apply { addAll(data.datas) }
                page = data.curPage + 1
                setRefreshStatus(false)
            } else {
                // 上拉加载更多
                setLoadMoreStart()
                val data = projectArticlesRepository.getProjectArticles(page, cid)
                val list = articles.value ?: mutableListOf()
                list.addAll(data.datas)
                articles.value = list
                page = data.curPage + 1
                setLoadMoreFinishStatus(data.offset, data.total)
            }
        })
    }
}