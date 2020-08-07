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
import com.cyl.wandroid.ui.adapter.HomeProjectAdapter
import com.cyl.wandroid.viewmodel.HomeNewestProjectViewModel
import kotlinx.android.synthetic.main.fragment_home_newest_article.*

/**
 * 首页最新项目Tab
 */
class HomeNewestProjectFragment :
    BaseRecyclerViewModelFragment<ArticleBean, HomeNewestProjectViewModel>(), OnItemClickListener {
    private lateinit var adapter: HomeProjectAdapter
    override fun getLayoutRes() = R.layout.fragment_home_newest_article

    override fun lazyInitData() {
        mViewModel.refreshProjects()
    }

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(mContext)
        recyclerView.layoutManager = manager
        adapter = HomeProjectAdapter()
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                mContext, DividerItemDecoration.VERTICAL
            )
        )
        adapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel.loadMoreProjects()
        }
        adapter.setOnItemClickListener(this)
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.refreshProjects() }
    }

    override fun observe() {
        super.observe()
        mViewModel.apply {
            projects.observe(viewLifecycleOwner, Observer {
                adapter.setList(it)
            })
        }
    }

    override fun getViewModelClass() = HomeNewestProjectViewModel::class.java

    override fun getAdapter() = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        IntentTools.start(mContext, AgentWebActivity::class.java, Bundle().apply {
            putString(AgentWebActivity.URL, mViewModel.projects.value?.get(position)?.link)
        })
    }
}
