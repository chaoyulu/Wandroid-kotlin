package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.bean.SystemCategoryBean
import com.cyl.wandroid.repository.SystemCategoryRepository

class SystemCategoryViewModel : BaseRecyclerViewModel() {
    private val systemCategoryRepository by lazy { SystemCategoryRepository() }
    val categories: MutableLiveData<List<SystemCategoryBean>> = MutableLiveData()

    fun getSystemCategory() {
        launch(block = {
            setRefreshStatus(true)
            categories.value =
                ArrayList<SystemCategoryBean>().apply {
                    addAll(systemCategoryRepository.getSystemCategory())
                }
            setRefreshStatus(false)
        })
    }
}