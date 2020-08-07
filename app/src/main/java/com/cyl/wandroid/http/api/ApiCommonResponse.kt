package com.cyl.wandroid.http.api

/**
 * {
 *      data : T
 *      errorCode :
 *      errorMsg :
 * }
 */
data class ApiCommonResponse<T>(val errorCode: Int, val errorMsg: String, val data: T) {
    fun getApiData(): T {
        if (errorCode == 0) return data
        else throw ApiException(errorCode, errorMsg)
    }
}