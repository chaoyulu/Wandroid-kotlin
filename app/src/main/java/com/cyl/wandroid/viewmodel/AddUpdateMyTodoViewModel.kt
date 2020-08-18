package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseViewModel
import com.cyl.wandroid.http.bean.TodoBean
import com.cyl.wandroid.repository.AddUpdateMyTodoRepository

class AddUpdateMyTodoViewModel : BaseViewModel() {
    private val addUpdateMyTodoRepository by lazy { AddUpdateMyTodoRepository() }
    val requestStatusLiveData = MutableLiveData<Boolean>() // true 请求开始 显示对话框 、false 请求结束 隐藏对话框
    val addTodoLiveData = MutableLiveData<TodoBean>()
    val updateTodoLiveData = MutableLiveData<TodoBean>()

    fun addMyTodo(title: String, content: String, date: String, type: Int = 0, priority: Int = 0) {
        launch(block = {
            requestStatusLiveData.value = true
            val todoBean = addUpdateMyTodoRepository.addMyTodo(title, content, date, type, priority)
            addTodoLiveData.value = todoBean
            requestStatusLiveData.value = false
        }, error = { requestStatusLiveData.value = false })
    }

    fun updateMyTodo(
        id: Int, title: String, content: String,
        date: String, status: Int, type: Int = 0, priority: Int = 0
    ) {
        launch(block = {
            requestStatusLiveData.value = true
            val todoBean = addUpdateMyTodoRepository.updateMyTodo(
                id, title, content, date, status, type, priority
            )
            updateTodoLiveData.value = todoBean
            requestStatusLiveData.value = false
        }, error = { requestStatusLiveData.value = false })
    }
}