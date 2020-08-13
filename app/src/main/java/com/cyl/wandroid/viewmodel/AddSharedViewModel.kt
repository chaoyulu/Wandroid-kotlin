package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.base.BaseRecyclerViewModel
import com.cyl.wandroid.http.api.RequestState
import com.cyl.wandroid.repository.AddSharedRepository
import kotlinx.coroutines.delay

class AddSharedViewModel : BaseRecyclerViewModel() {
    private val addSharedRepository by lazy { AddSharedRepository() }

    val requestState: MutableLiveData<RequestState> = MutableLiveData()
    val sharedSuccess: MutableLiveData<Boolean> = MutableLiveData()

    fun submitShare(title: String, link: String) {
        launch(block = {
            requestState.value = RequestState.START
//            addSharedRepository.addShared(title, link)
            delay(3000)
            sharedSuccess.value = true
            requestState.value = RequestState.END
        }, error = {
            sharedSuccess.value = false
            requestState.value = RequestState.END
        })
    }

    fun getUserInfo() = addSharedRepository.getUserInfo()
}