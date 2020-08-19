package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.repository.HomeNewestProjectRepository

class HomeNewestProjectViewModel : BaseRecyclerViewModel() {
    private val homeNewestProjectRepository by lazy { HomeNewestProjectRepository() }
    private var page = 0
    private val pageStart = 0

    val projects: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()

    fun refreshProjects() {
        page = pageStart
        getHomeNewestProjects()
    }

    fun loadMoreProjects() {
        getHomeNewestProjects()
    }

    // pageInit = 0则视为刷新，否则就是加载更多
    private fun getHomeNewestProjects() {
        launch(block = {
            if (page == pageStart) {
                val data = homeNewestProjectRepository.getHomeNewestProject(pageStart)
                setRefreshStatus(true)
                // 下拉刷新
                projects.value = mutableListOf<ArticleBean>().apply { addAll(data.datas) }
                page = data.curPage
                setRefreshStatus(false)
            } else {
                // 上拉加载更多
                setLoadMoreStart()
                val data = homeNewestProjectRepository.getHomeNewestProject(page)
                val list = projects.value ?: mutableListOf()
                list.addAll(data.datas)
                projects.value = list
                page = data.curPage
                setLoadMoreFinishStatus(data.offset, data.total)
            }
        })
    }
}