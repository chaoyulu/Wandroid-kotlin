package com.cyl.wandroid.ui.activity

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseRecyclerViewModelActivity
import com.cyl.wandroid.ext.hideSoftInput
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.tools.checkLoginThenAction
import com.cyl.wandroid.tools.showError
import com.cyl.wandroid.ui.adapter.SearchAdapter
import com.cyl.wandroid.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.layout_swipe_recycler.*
import kotlinx.android.synthetic.main.toolbar_search_activity.*

class SearchActivity : BaseRecyclerViewModelActivity<ArticleBean, SearchViewModel>(),
    OnItemClickListener, OnItemChildClickListener {

    private lateinit var adapter: SearchAdapter
    private var key: String = ""

    override fun getAdapter() = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        adapter = SearchAdapter()
        recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener { mViewModel.loadMoreSearch(key) }
        adapter.setOnItemClickListener(this)
        adapter.setOnItemChildClickListener(this)
        adapter.setEmptyView(R.layout.layout_empty_view)
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            search()
        }
    }

    override fun getViewModelClass() = SearchViewModel::class.java
    override fun getViewModelArticles(): MutableLiveData<MutableList<ArticleBean>> {
        return mViewModel.articles
    }

    override fun initData() {
        tvSearch.setOnClickListener {
            it.hideSoftInput()
            search()
        }
    }

    private fun search() {
        key = getKey()
        if (key.isEmpty()) {
            showError("请输入关键词")
            return
        }

        mViewModel.refreshSearch(key)
    }

    private fun getKey() = etSearch.text.toString()

    override fun getLayoutRes() = R.layout.activity_search
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        onItemClickToAgentWeb(position)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (view.id == R.id.ivCollection && checkLoginThenAction(this)) {
            collectItemChildClick(position, false)
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.apply {
            articles.observe(this@SearchActivity, Observer {
                adapter.setSearchKey(key)
                adapter.setList(it)
            })
        }
    }
}