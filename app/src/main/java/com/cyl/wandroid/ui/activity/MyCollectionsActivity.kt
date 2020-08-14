package com.cyl.wandroid.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseRecyclerViewModelActivity
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.tools.start
import com.cyl.wandroid.ui.adapter.MyCollectionsAdapter
import com.cyl.wandroid.viewmodel.MyCollectionsViewModel
import kotlinx.android.synthetic.main.layout_swipe_recycler.*

/**
 * 我的收藏列表页面
 */
class MyCollectionsActivity : BaseRecyclerViewModelActivity<ArticleBean, MyCollectionsViewModel>(),
    OnItemClickListener, OnItemChildClickListener {
    private lateinit var adapter: MyCollectionsAdapter

    override fun getAdapter() = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        adapter = MyCollectionsAdapter()
        recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener { mViewModel.loadMoreCollections() }
        adapter.setOnItemClickListener(this)
        adapter.setOnItemChildClickListener(this)
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.refreshCollections() }
    }

    override fun getViewModelClass() = MyCollectionsViewModel::class.java

    override fun initData() {
        mViewModel.refreshCollections()
    }

    override fun initView() {
        super.initView()
        setCenterText(R.string.my_collections)
    }

    override fun getLayoutRes() = R.layout.activity_my_collections

    override fun observe() {
        super.observe()
        mViewModel.apply {
            collectionsList.observe(this@MyCollectionsActivity, Observer {
                adapter.setList(it)
            })
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        start(this, AgentWebActivity::class.java, Bundle().apply {
            putString(AgentWebActivity.URL, mViewModel.collectionsList.value?.get(position)?.link)
        })
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
    }
}