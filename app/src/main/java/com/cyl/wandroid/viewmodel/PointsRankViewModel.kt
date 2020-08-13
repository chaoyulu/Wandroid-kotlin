package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.bean.PointRankBean
import com.cyl.wandroid.repository.PointsRankRepository

class PointsRankViewModel : BaseRecyclerViewModel() {
    private val pointsRankRepository by lazy { PointsRankRepository() }

    val pointsRankList: MutableLiveData<MutableList<PointRankBean>> = MutableLiveData()
    private val pageStart = 1
    private var page = pageStart // 从第一页开始

    // 获取我的积分信息及第一页的积分记录
    fun refreshPointsRank() {
        launch(block = {
            page = pageStart
            getMyPointsRank()
        })
    }

    fun loadMorePointsList() {
        getMyPointsRank()
    }

    private fun getMyPointsRank() {
        launch(block = {
            if (page == pageStart) {
                // 下拉刷新
                setRefreshStatus(true)
                val list = pointsRankRepository.getPointsRank(page)
                pointsRankList.value = mutableListOf<PointRankBean>().apply { addAll(list.datas) }
                page = list.curPage + 1
                setRefreshStatus(false)
            } else {
                // 上拉加载更多
                setLoadMoreStart()
                val data = pointsRankRepository.getPointsRank(page)
                val list = pointsRankList.value ?: mutableListOf()
                list.addAll(data.datas)
                pointsRankList.value = list
                page = data.curPage + 1
                setLoadMoreFinishStatus(data.offset, data.total)
            }
        })
    }
}