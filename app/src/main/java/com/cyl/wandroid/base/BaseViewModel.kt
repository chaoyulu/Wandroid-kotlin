package com.cyl.wandroid.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cyl.wandroid.http.api.ApiException
import com.cyl.wandroid.tools.showError
import com.google.gson.JsonParseException
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException

typealias Error = suspend (e: Exception) -> Unit

open class BaseViewModel : ViewModel() {
    protected open fun launch(
        block: suspend () -> Unit,
        error: Error? = null
    ): Job {
        return viewModelScope.launch {
            try {
                block()
            } catch (e: Exception) {
                parseError(e)
                onError()
                error?.invoke(e) // 捕获ApiCommonResponse类中的异常信息
            }
        }
    }

    // 用来修改状态等（界面刷新状态、加载状态）
    protected open fun onError() {

    }

    private fun parseError(e: Exception) {
        when (e) {
            is ApiException -> {
                when (e.errorCode) {
                    -1001 -> {
                        // TODO 未登录
                        showError("未登录")
                    }
                    else -> {
                        // TODO 其他错误
                        showError(e.errorMsg)
                    }
                }
            }

            is ConnectException -> {
                // TODO 连接失败
            }

            is SocketTimeoutException -> {
                // TODO 请求超时
            }

            is JsonParseException -> {
                // TODO JSON解析错误
            }

            else -> {
                // TODO 其他错误
                e.message?.let { showError(it) }
            }
        }
    }

    protected fun <T> async(block: suspend () -> T): Deferred<T> {
        return viewModelScope.async { block() }
    }
}