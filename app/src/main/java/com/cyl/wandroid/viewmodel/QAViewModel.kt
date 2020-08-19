package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.repository.QaRepository

class QAViewModel : BaseRecyclerViewModel() {
    private val qaRepository by lazy { QaRepository() }

    val qaLiveData = MutableLiveData<MutableList<ArticleBean>>()
    private val pageStart = 1
    private var page = pageStart

    fun refreshQA() {
        page = pageStart
        getQA()
    }

    fun loadMoreQA() {
        getQA()
    }

    private fun getQA() {
        launch(block = {
            if (page == pageStart) {
                val data = qaRepository.getQA(pageStart)
                setRefreshStatus(true)
                // 下拉刷新
                qaLiveData.value = mutableListOf<ArticleBean>().apply { addAll(data.datas) }
                page = data.curPage + 1
                setRefreshStatus(false)
            } else {
                // 上拉加载更多
                setLoadMoreStart()
                val data = qaRepository.getQA(page)
                val list = qaLiveData.value ?: mutableListOf()
                list.addAll(data.datas)
                qaLiveData.value = list
                page = data.curPage + 1
                setLoadMoreFinishStatus(data.offset, data.total)
            }
        })
    }
}