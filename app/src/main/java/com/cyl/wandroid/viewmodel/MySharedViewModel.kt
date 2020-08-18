package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.repository.MySharedRepository

class MySharedViewModel : CollectViewModel() {
    private val mySharedRepository by lazy { MySharedRepository() }

    val sharedList: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()

    // Pair<Int, Boolean> Int是删除分享的索引，Boolean -true 删除开始 showDialog,false 删除结束 dismissDialog
    val deleteShareLiveData = MutableLiveData<Pair<Int, Boolean>>()

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

    fun deleteMyShare(id: Int) {
        launch(block = {
            deleteShareLiveData.value = -1 to true
            mySharedRepository.deleteMyShare(id)
            var index = -1
            sharedList.value?.forEachIndexed { i, articleBean ->
                if (articleBean.id == id) {
                    index = i
                    return@forEachIndexed
                }
            }
            sharedList.value?.removeAt(index)
            deleteShareLiveData.value = index to false
        }, error = { deleteShareLiveData.value = -1 to false })
    }
}