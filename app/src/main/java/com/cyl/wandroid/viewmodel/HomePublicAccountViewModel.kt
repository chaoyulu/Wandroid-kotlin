package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.bean.PublicAccountBean
import com.cyl.wandroid.repository.HomePublicAccountRepository

class HomePublicAccountViewModel : BaseRecyclerViewModel() {
    private val homePublicAccountRepository by lazy { HomePublicAccountRepository() }
    val accounts: MutableLiveData<ArrayList<PublicAccountBean>> = MutableLiveData()

    fun getHomePublicAccountList() {
        launch(block = {
            setRefreshStatus(true)
            val data = homePublicAccountRepository.getPublicAccountList()
            accounts.value = ArrayList<PublicAccountBean>().apply {
                addAll(data)
            }
            setRefreshStatus(false)
        })
    }
}