package com.cyl.wandroid.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseRecyclerViewModelFragment
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.tools.IntentTools
import com.cyl.wandroid.ui.activity.AgentWebActivity
import com.cyl.wandroid.ui.adapter.HomeShareAdapter
import com.cyl.wandroid.viewmodel.HomeNewestShareViewModel
import kotlinx.android.synthetic.main.layout_swipe_recycler.*

/**
 * 首页最新分享Tab
 */
class HomeNewestShareFragment :
    BaseRecyclerViewModelFragment<ArticleBean, HomeNewestShareViewModel>(), OnItemClickListener {
    private lateinit var adapter: HomeShareAdapter

    override fun getLayoutRes() = R.layout.layout_swipe_recycler

    override fun getAdapter() = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout

    override fun lazyInitData() {
        mViewModel.refreshShare()
    }

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(mContext)
        recyclerView.layoutManager = manager
        adapter = HomeShareAdapter()
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL)
        )
        adapter.loadMoreModule.setOnLoadMoreListener { mViewModel.loadMoreShare() }
        adapter.setOnItemClickListener(this)
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.refreshShare() }
    }

    override fun getViewModelClass() = HomeNewestShareViewModel::class.java

    override fun observe() {
        super.observe()
        mViewModel.apply {
            articles.observe(viewLifecycleOwner, Observer {
                adapter.setList(it)
            })
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        IntentTools.start(mContext, AgentWebActivity::class.java, Bundle().apply {
            putString(AgentWebActivity.URL, mViewModel.articles.value?.get(position)?.link)
        })
    }
}
