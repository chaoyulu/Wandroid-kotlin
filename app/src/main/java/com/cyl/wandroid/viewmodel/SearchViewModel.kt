package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.HotKeyBean
import com.cyl.wandroid.repository.SearchRepository

class SearchViewModel : CollectViewModel() {
    private val searchRepository by lazy { SearchRepository() }
    val articles = MutableLiveData<MutableList<ArticleBean>>()
    val hotKeysLiveData = MutableLiveData<List<HotKeyBean>>()

    private val pageStart = 0
    private var page = pageStart

    fun refreshSearch(k: String) {
        page = pageStart
        search(k)
    }

    fun loadMoreSearch(k: String) {
        search(k)
    }

    private fun search(k: String) {
        launch(block = {
            if (page == pageStart) {
                // 下拉刷新
                setRefreshStatus(true)
                val data = searchRepository.search(pageStart, k)
                articles.value = mutableListOf<ArticleBean>().apply { addAll(data.datas) }
                page = data.curPage
                setRefreshStatus(false)
            } else {
                // 上拉加载更多
                setLoadMoreStart()
                val data = searchRepository.search(page, k)
                val list = articles.value ?: mutableListOf()
                list.addAll(data.datas)
                articles.value = list
                page = data.curPage
                setLoadMoreFinishStatus(data.offset, data.total)
            }
        })
    }

    fun getHotKey() {
        launch(block = {
            hotKeysLiveData.value = searchRepository.getHotKey()
        })
    }
}