package com.cyl.wandroid.base

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadmore.LoadMoreStatus

/**
 * 封装在RecyclerView界面使用的ViewModel
 * 封装了LoadMoreStatus和RefreshStatus，就不需要在每个界面的ViewModel中写一遍了
 */
open class BaseRecyclerViewModel : BaseViewModel() {
    val refreshStatus: MutableLiveData<Boolean> = MutableLiveData()
    val loadMoreStatus: MutableLiveData<LoadMoreStatus> = MutableLiveData()

    override fun onError() {
        super.onError()
        refreshStatus.value = false
        loadMoreStatus.value = LoadMoreStatus.Fail
    }

    fun setRefreshStatus(status: Boolean) {
        refreshStatus.value = status
    }

    fun setLoadMoreFinishStatus(offset: Int, total: Int) {
        loadMoreStatus.value = if (offset >= total) LoadMoreStatus.End else LoadMoreStatus.Complete
    }

    fun setLoadMoreStart() {
        loadMoreStatus.value = LoadMoreStatus.Loading
    }
}