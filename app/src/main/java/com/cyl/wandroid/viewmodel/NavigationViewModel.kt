package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.bean.NavigationBean
import com.cyl.wandroid.repository.NavigationRepository

class NavigationViewModel : BaseRecyclerViewModel() {
    private val navigationRepository by lazy { NavigationRepository() }
    val navigation: MutableLiveData<List<NavigationBean>> = MutableLiveData()

    fun getNavigation() {
        launch(block = {
            setRefreshStatus(true)
            navigation.value = mutableListOf<NavigationBean>().apply {
                addAll(navigationRepository.getNavigation())
            }
            setRefreshStatus(false)
        })
    }
}