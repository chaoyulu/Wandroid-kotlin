package com.cyl.wandroid.ui.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseRecyclerViewModelActivity
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.tools.IntentTools
import com.cyl.wandroid.ui.adapter.MySharedAdapter
import com.cyl.wandroid.viewmodel.MySharedViewModel
import kotlinx.android.synthetic.main.layout_swipe_recycler.*

class MySharedActivity : BaseRecyclerViewModelActivity<ArticleBean, MySharedViewModel>(),
    OnItemClickListener {
    private lateinit var adapter: MySharedAdapter

    override fun getAdapter() = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        adapter = MySharedAdapter()
        recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener { mViewModel.loadMoreShared() }
        adapter.setOnItemClickListener(this)
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.refreshShared() }
    }

    override fun getViewModelClass() = MySharedViewModel::class.java

    override fun initData() {
        mViewModel.refreshShared()
    }

    override fun initView() {
        super.initView()
        setCenterText(R.string.my_shared)
        setRightIcon(R.mipmap.icon_add)
    }

    override fun getLayoutRes() = R.layout.activity_my_shared

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        IntentTools.start(this, AgentWebActivity::class.java, Bundle().apply {
            putString(AgentWebActivity.URL, mViewModel.sharedList.value?.get(position)?.link)
        })
    }

    override fun observe() {
        super.observe()
        mViewModel.apply {
            sharedList.observe(this@MySharedActivity, Observer { adapter.setList(it) })
        }
    }

    override fun onRightIconClick() {
        super.onRightIconClick()
        IntentTools.start(this, AddShareActivity::class.java, needLogin = true)
    }
}