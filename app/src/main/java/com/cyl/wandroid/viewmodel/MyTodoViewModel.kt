package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.bean.TodoBean
import com.cyl.wandroid.repository.MyTodoRepository

class MyTodoViewModel : BaseRecyclerViewModel() {
    private val myTodoRepository by lazy { MyTodoRepository() }
    private val pageStart = 1
    private var page = pageStart
    val todoLiveData: MutableLiveData<MutableList<TodoBean>> = MutableLiveData()

    fun refreshMyTodo(status: Int) {
        page = pageStart
        getMyTodo(status)
    }

    fun loadMoreMyTodo(status: Int) {
        getMyTodo(status)
    }

    private fun getMyTodo(
        status: Int, // 1-完成  0-未完成
        type: Int = 0, // 0全部,类型筛选
        priority: Int = 0, // 优先级 1-重要  2-一般 0-全部
        orderby: Int = 4 // 排序 1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)
    ) {
        launch(block = {
            if (page == pageStart) {
                // 下拉刷新
                setRefreshStatus(true)
                val data = myTodoRepository.getMyTodo(page, status, type, priority, orderby)
                todoLiveData.value =
                    mutableListOf<TodoBean>().apply { addAll(data.datas) }
                page = data.curPage + 1
                setRefreshStatus(false)
            } else {
                // 上拉加载更多
                setLoadMoreStart()
                val data = myTodoRepository.getMyTodo(page, status, type, priority, orderby)
                val list = todoLiveData.value ?: mutableListOf()
                list.addAll(data.datas)
                todoLiveData.value = list
                page = data.curPage + 1
                setLoadMoreFinishStatus(data.offset, data.total)
            }
        })
    }
}