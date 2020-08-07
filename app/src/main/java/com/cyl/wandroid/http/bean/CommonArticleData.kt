package com.cyl.wandroid.http.bean

/**
 * 和文章相关接口的响应数据封装
 * "data":{
 *      "curPage":1,
 *      "datas":Array[20],
 *      "offset":0,
 *      "over":false,
 *      "pageCount":432,
 *      "size":20,
 *      "total":8632
 * }
 *
 * 很多接口data字段下的字段都一样，只是datas下的字段不一样，因此需要封装起来
 */
data class CommonArticleData<T>(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: List<T>
)