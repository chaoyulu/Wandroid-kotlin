package com.cyl.wandroid.ui.activity

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
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
import com.cyl.wandroid.ext.setCircularCorner
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.HotKeyBean
import com.cyl.wandroid.listener.OnSearchViewClickListener
import com.cyl.wandroid.tools.checkLoginThenAction
import com.cyl.wandroid.ui.adapter.SearchAdapter
import com.cyl.wandroid.ui.widget.SearchHistoryView
import com.cyl.wandroid.viewmodel.SearchViewModel
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.layout_search_history_view.*
import kotlinx.android.synthetic.main.layout_swipe_recycler.*
import kotlinx.android.synthetic.main.layout_swipe_recycler.recyclerView
import kotlinx.android.synthetic.main.toolbar_search_activity.*

class SearchActivity : BaseRecyclerViewModelActivity<ArticleBean, SearchViewModel>(),
    OnItemClickListener, OnItemChildClickListener, OnSearchViewClickListener {

    private lateinit var adapter: SearchAdapter
    private var key: String = ""
    private lateinit var searchHistoryView: SearchHistoryView

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
        searchHistoryView = SearchHistoryView(this, this)
        adapter.setEmptyView(R.layout.layout_search_history_view)
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
            search()
        }
    }

    private fun search() {
        tvSearch.hideSoftInput()
        key = getKey()
        if (key.isEmpty()) {
            mViewModel.setRefreshStatus(false)
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

            hotKeysLiveData.observe(this@SearchActivity, Observer {
                showHotKey(it)
            })
        }
    }

    private fun showHotKey(hotKeys: List<HotKeyBean>) {
        val tagAdapter = object : TagAdapter<HotKeyBean>(hotKeys) {
            override fun getView(
                parent: FlowLayout?, position: Int, t: HotKeyBean
            ): View {
                val tv = LayoutInflater.from(this@SearchActivity)
                    .inflate(R.layout.tag_view, tagFlowLayout, false) as TextView
                val bgColor = Color.LTGRAY
                val tagTextColor = Color.BLACK
                tv.setTextColor(tagTextColor)
                tv.setCircularCorner(bgColor)
                tv.text = t.name
                return tv
            }
        }
        tagFlowLayout.adapter = tagAdapter
        tagFlowLayout.setOnTagClickListener { _, position, _ ->
            val key = mViewModel.hotKeysLiveData.value?.get(position)?.name
            key?.let {
                etSearch.setText(it)
                search()
            }
            return@setOnTagClickListener true
        }
    }

    override fun getHotKeys() {
        mViewModel.getHotKey()
    }

    override fun getSearchHistoryKeys() {
        // TODO 获取保存在本地的搜索记录
    }
}