package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseViewModel
import com.cyl.wandroid.http.api.ApiEngine
import com.cyl.wandroid.http.bean.UserBean
import com.cyl.wandroid.sp.UserSpHelper

class MineViewModel : BaseViewModel() {
    val logoutLiveData: MutableLiveData<Boolean> = MutableLiveData()

    fun getUserInfo(): UserBean? {
        return UserSpHelper.newHelper().getUserInfo()
    }

    fun logout() {
        UserSpHelper.newHelper().clearUserInfo()
        ApiEngine.cookieJar.clear()
        logoutLiveData.value = true
    }
}