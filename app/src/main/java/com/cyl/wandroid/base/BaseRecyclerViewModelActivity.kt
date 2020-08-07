package com.cyl.wandroid.base

import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * 在使用RecyclerView的Activity界面使用
 */
abstract class BaseRecyclerViewModelActivity<T, VM : BaseRecyclerViewModel> :
    BaseViewModelActivity<VM>() {
    override fun observe() {
        observeStatus(getAdapter(), getSwipeRefreshLayout())
    }

    private fun observeStatus(
        adapter: BaseQuickAdapter<T, BaseViewHolder>,
        swipeRefreshLayout: SwipeRefreshLayout
    ) {
        mViewModel.loadMoreStatus.observe(this, Observer {
            when (it) {
                LoadMoreStatus.Complete -> adapter.loadMoreModule.loadMoreComplete()
                LoadMoreStatus.Fail -> adapter.loadMoreModule.loadMoreFail()
                LoadMoreStatus.End -> adapter.loadMoreModule.loadMoreEnd()
                else -> return@Observer
            }
        })

        mViewModel.refreshStatus.observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it
        })
    }

    override fun initView() {
        initRecyclerView()
        initRefreshLayout()
    }

    abstract fun getAdapter(): BaseQuickAdapter<T, BaseViewHolder>
    abstract fun getSwipeRefreshLayout(): SwipeRefreshLayout
    abstract fun initRecyclerView()
    abstract fun initRefreshLayout()
}