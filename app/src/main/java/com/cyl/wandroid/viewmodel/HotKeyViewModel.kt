package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseViewModel
import com.cyl.wandroid.http.bean.HotKeyBean
import com.cyl.wandroid.repository.HotKeyRepository

class HotKeyViewModel : BaseViewModel() {
    private val hotKeyRepository by lazy { HotKeyRepository() }
    val hotKeysLiveData = MutableLiveData<List<HotKeyBean>>()

    fun getHotKey() {
        launch(block = {
            hotKeysLiveData.value = hotKeyRepository.getHotKey()
        })
    }
}