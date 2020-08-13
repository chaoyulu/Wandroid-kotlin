package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.repository.OthersSharedRepository

class OthersSharedViewModel : BaseRecyclerViewModel() {
    private val othersSharedRepository by lazy { OthersSharedRepository() }

    val sharedList: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()
    private val pageStart = 1
    private var page = pageStart

    fun refreshShared(id: Int) {
        page = pageStart
        getSharedList(id)
    }

    fun loadMoreShared(id: Int) {
        getSharedList(id)
    }

    private fun getSharedList(id: Int) {
        launch(block = {
            if (page == pageStart) {
                // 下拉刷新
                setRefreshStatus(true)
                val data = othersSharedRepository.getOthersShared(id, page)
                sharedList.value =
                    mutableListOf<ArticleBean>().apply { addAll(data.shareArticles.datas) }
                page = data.shareArticles.curPage + 1
                setRefreshStatus(false)
            } else {
                // 上拉加载更多
                setLoadMoreStart()
                val data = othersSharedRepository.getOthersShared(id, page)
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