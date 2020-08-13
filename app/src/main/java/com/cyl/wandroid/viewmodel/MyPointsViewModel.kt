package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.bean.PointRankBean
import com.cyl.wandroid.http.bean.PointsListBean
import com.cyl.wandroid.repository.MyPointsRepository

class MyPointsViewModel : BaseRecyclerViewModel() {
    private val myPointsRepository by lazy { MyPointsRepository() }

    val pointsInfo: MutableLiveData<PointRankBean> = MutableLiveData()
    val pointsList: MutableLiveData<MutableList<PointsListBean>> = MutableLiveData()
    private var page = 1 // 从第一页开始

    // 获取我的积分信息及第一页的积分记录
    fun getMyPointsInfo() {
        launch(block = {
            page = 1
            pointsInfo.value = myPointsRepository.getMyPointsInfo()
            getMyPointsList()
        })
    }

    fun loadMorePointsList() {
        getMyPointsList()
    }

    private fun getMyPointsList() {
        launch(block = {
            if (page == 1) {
                // 下拉刷新
                setRefreshStatus(true)
                val list = myPointsRepository.getMyPointsList(page)
                pointsList.value = mutableListOf<PointsListBean>().apply { addAll(list.datas) }
                page = list.curPage + 1
                setRefreshStatus(false)
            } else {
                // 上拉加载更多
                setLoadMoreStart()
                val data = myPointsRepository.getMyPointsList(page)
                val list = pointsList.value ?: mutableListOf()
                list.addAll(data.datas)
                pointsList.value = list
                page = data.curPage + 1
                setLoadMoreFinishStatus(data.offset, data.total)
            }
        })
    }
}