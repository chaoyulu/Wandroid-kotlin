package com.cyl.wandroid.base

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.loadmore.LoadMoreStatus
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.CANCEL_COLLECT_SUCCESS
import com.cyl.wandroid.common.bus.COLLECT_SUCCESS
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.tools.start
import com.cyl.wandroid.ui.activity.AgentWebActivity
import com.cyl.wandroid.viewmodel.CollectViewModel

/**
 * 在使用RecyclerView的Activity界面使用
 */
abstract class BaseRecyclerViewModelActivity<T, VM : BaseRecyclerViewModel> :
    BaseViewModelActivity<VM>() {
    override fun observe() {
        observeStatus()

        if (mViewModel is CollectViewModel) {
            (mViewModel as CollectViewModel).collectLiveData.observe(this, Observer {
                (mViewModel as CollectViewModel).updateCollectStatus(
                    it.first,
                    it.second,
                    getViewModelArticles()
                )
            })
        }
    }

    private fun observeStatus() {
        mViewModel.loadMoreStatus.observe(this, Observer {
            when (it) {
                LoadMoreStatus.Complete -> getAdapter().loadMoreModule.loadMoreComplete()
                LoadMoreStatus.Fail -> getAdapter().loadMoreModule.loadMoreFail()
                LoadMoreStatus.End -> getAdapter().loadMoreModule.loadMoreEnd()
                else -> return@Observer
            }
        })

        mViewModel.refreshStatus.observe(this, Observer {
            getSwipeRefreshLayout().isRefreshing = it
        })
    }

    override fun initView() {
        initRecyclerView()
        initRefreshLayout()
        collectBusObserve()
    }

    abstract fun getAdapter(): BaseQuickAdapter<T, BaseViewHolder>
    abstract fun getSwipeRefreshLayout(): SwipeRefreshLayout
    abstract fun initRecyclerView()
    abstract fun initRefreshLayout()


    // 文章收藏的封装
    open fun collectBusObserve() {
        Bus.observe<Int>(COLLECT_SUCCESS, this, observer = {
            // it是文章id
            (mViewModel as CollectViewModel).updateCollectStatus(it, true, getViewModelArticles())
        })
        Bus.observe<Int>(CANCEL_COLLECT_SUCCESS, this, observer = {
            // it是文章id
            (mViewModel as CollectViewModel).updateCollectStatus(it, false, getViewModelArticles())
        })
    }

    // 如果页面有收藏按钮，必须重写
    open fun getViewModelArticles(): MutableLiveData<MutableList<ArticleBean>> {
        return MutableLiveData()
    }

    // 文章列表点击收藏按钮触发的事件A封装
    /**
     * 文章列表点击收藏按钮触发的事件封装
     * isFromCollectActivity 是否是MyCollectionsActivity
     */
    open fun collectItemChildClick(position: Int, isFromCollectActivity: Boolean = true) {
        val viewModel = mViewModel as CollectViewModel
        getViewModelArticles().value?.get(position)?.let {
            if (it.collect) {
                if (isFromCollectActivity) {
                    viewModel.cancelCollectFromCollectionList(it.id, it.originId)
                } else {
                    viewModel.cancelCollectFromArticleList(it.id)
                }
            } else {
                viewModel.collect(it.id)
            }
        }
    }

    // 取消收藏
    open fun cancelCollectItemClick(position: Int) {
        val viewModel = mViewModel as CollectViewModel
        getViewModelArticles().value?.get(position)?.let {
            viewModel.cancelCollectFromCollectionList(it.id, it.originId)
        }
    }

    open fun onItemClickToAgentWeb(position: Int, showCollectItem: Boolean = true) {
        start(this, AgentWebActivity::class.java, Bundle().apply {
            val articles = getViewModelArticles().value
            // 链接
            putString(AgentWebActivity.URL, articles?.get(position)?.link)
            // 收藏
            articles?.get(position)?.collect?.let {
                putBoolean(AgentWebActivity.IS_COLLECT, it)
            }
            // id
            articles?.get(position)?.id?.let { putInt(AgentWebActivity.ID, it) }
            putBoolean(AgentWebActivity.SHOW_COLLECT_ITEM, showCollectItem)
        })
    }
}