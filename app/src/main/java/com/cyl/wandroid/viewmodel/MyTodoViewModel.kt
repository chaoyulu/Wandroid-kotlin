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
    val requestStatusLiveData = MutableLiveData<Boolean>() // true 请求开始 显示对话框 、false 请求结束 隐藏对话框
    val deleteTodoLiveData = MutableLiveData<Int>() // Int是索引
    val updateTodoLiveData = MutableLiveData<Pair<Int, TodoBean>>() // 返回修改状态成功后的索引和TodoBean

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

    fun deleteMyTodo(id: Int) {
        launch(block = {
            requestStatusLiveData.value = true
            myTodoRepository.deleteMyTodo(id)
            var index = -1
            todoLiveData.value?.forEachIndexed { i, todoBean ->
                if (todoBean.id == id) {
                    index = i
                    return@forEachIndexed
                }
            }
            todoLiveData.value?.removeAt(index)
            deleteTodoLiveData.value = index
            requestStatusLiveData.value = false
        }, error = {
            requestStatusLiveData.value = false
        })
    }

    fun updateMyTodoStatus(id: Int, status: Int) {
        launch(block = {
            requestStatusLiveData.value = true
            val todoBean = myTodoRepository.updateMyTodoStatus(id, status)

            var index = -1
            todoLiveData.value?.forEachIndexed { i, bean ->
                if (bean.id == id) {
                    index = i
                    return@forEachIndexed
                }
            }
            todoLiveData.value?.removeAt(index)
            updateTodoLiveData.value = index to todoBean
            requestStatusLiveData.value = false
        }, error = { requestStatusLiveData.value = false })
    }

    // 添加成功，将新添加的放在第一个
    fun addTodoSuccess(todoBean: TodoBean) {
        val list = todoLiveData.value
        list?.add(0, todoBean)
        todoLiveData.value = list
    }

    // 修改成功，根据id搜索，将其修改
    fun updateTodoSuccess(todoBean: TodoBean) {
        // 当前页面删除，另一页面增加
        todoLiveData.value?.forEachIndexed { index, bean ->
            if (todoBean.id == bean.id) {
                val list = todoLiveData.value
                list?.set(index, todoBean)
                todoLiveData.value = list
                return@forEachIndexed
            }
        }
    }
}