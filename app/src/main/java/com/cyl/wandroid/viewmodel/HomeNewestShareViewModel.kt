package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.repository.HomeNewestShareRepository

class HomeNewestShareViewModel : BaseRecyclerViewModel() {
    private val homeNewestShareRepository by lazy { HomeNewestShareRepository() }
    private var page = 0

    val articles: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()

    fun refreshShare() {
        page = 0
        getHomeNewestShare()
    }

    fun loadMoreShare() {
        getHomeNewestShare()
    }

    // pageInit = 0则视为刷新，否则就是加载更多
    private fun getHomeNewestShare() {
        launch(block = {
            if (page == 0) {
                // 下拉刷新
                setRefreshStatus(true)
                val data = homeNewestShareRepository.getHomeNewestShare(0)
                articles.value = mutableListOf<ArticleBean>().apply { addAll(data.datas) }
                page = data.curPage
                setRefreshStatus(false)
            } else {
                // 上拉加载更多
                setLoadMoreStart()
                val data = homeNewestShareRepository.getHomeNewestShare(page)
                val list = articles.value ?: mutableListOf()
                list.addAll(data.datas)
                articles.value = list
                page = data.curPage
                setLoadMoreFinishStatus(data.offset, data.total)
            }
        })
    }
}