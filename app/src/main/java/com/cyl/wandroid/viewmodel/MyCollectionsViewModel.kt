package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.repository.MyCollectionsRepository

class MyCollectionsViewModel : BaseRecyclerViewModel() {
    private val myCollectionsRepository by lazy { MyCollectionsRepository() }

    val collectionsList: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()
    private val pageStart = 0
    private var page = pageStart

    fun refreshCollections() {
        page = pageStart
        getCollectionsList()
    }

    fun loadMoreCollections() {
        getCollectionsList()
    }

    private fun getCollectionsList() {
        launch(block = {
            if (page == pageStart) {
                // 下拉刷新
                setRefreshStatus(true)
                val list = myCollectionsRepository.getMyCollections(page)
                collectionsList.value = mutableListOf<ArticleBean>().apply { addAll(list.datas) }
                page = list.curPage
                setRefreshStatus(false)
            } else {
                // 上拉加载更多
                setLoadMoreStart()
                val data = myCollectionsRepository.getMyCollections(page)
                val list = collectionsList.value ?: mutableListOf()
                list.addAll(data.datas)
                collectionsList.value = list
                page = data.curPage
                setLoadMoreFinishStatus(data.offset, data.total)
            }
        })
    }
}