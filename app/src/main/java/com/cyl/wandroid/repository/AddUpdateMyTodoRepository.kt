package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.TodoBean

class AddUpdateMyTodoRepository {
    suspend fun addMyTodo(
        title: String,
        content: String,
        date: String,
        type: Int,
        priority: Int
    ): TodoBean {
        return ApiEngine.getApiService().addMyTodo(title, content, date, type, priority)
            .getApiData()
    }

    suspend fun updateMyTodo(
        id: Int, title: String, content: String, date: String,
        status: Int, type: Int, priority: Int
    ): TodoBean {
        return ApiEngine.getApiService()
            .updateMyTodo(id, title, content, date, status, type, priority)
            .getApiData()
    }
}