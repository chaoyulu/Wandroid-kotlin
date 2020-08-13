package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.repository.MySharedRepository

class MySharedViewModel : BaseRecyclerViewModel() {
    private val mySharedRepository by lazy { MySharedRepository() }

    val sharedList: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()
    private val pageStart = 1
    private var page = pageStart

    fun refreshShared() {
        page = pageStart
        getSharedList()
    }

    fun loadMoreShared() {
        getSharedList()
    }

    private fun getSharedList() {
        launch(block = {
            if (page == pageStart) {
                // 下拉刷新
                setRefreshStatus(true)
                val data = mySharedRepository.getMyShared(page)
                sharedList.value =
                    mutableListOf<ArticleBean>().apply { addAll(data.shareArticles.datas) }
                page = data.shareArticles.curPage + 1
                setRefreshStatus(false)
            } else {
                // 上拉加载更多
                setLoadMoreStart()
                val data = mySharedRepository.getMyShared(page)
                val sharedArticles = data.shareArticles

                val list = sharedList.value ?: mutableListOf()
                list.addAll(sharedArticles.datas)
                sharedList.value = list
                page = sharedArticles.curPage + 1
                setLoadMoreFinishStatus(sharedArticles.offset, sharedArticles.total)
            }
        })
    }
}