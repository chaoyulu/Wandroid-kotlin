package com.cyl.wandroid.repository

import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.CommonArticleData
import com.cyl.wandroid.http.bean.TodoBean

class MyTodoRepository {
    suspend fun getMyTodo(
        page: Int,
        status: Int,
        type: Int,
        priority: Int,
        orderby: Int
    ): CommonArticleData<TodoBean> {
        return ApiEngine.getApiService().getMyTodo(page, status, type, priority, orderby)
            .getApiData()
    }
}