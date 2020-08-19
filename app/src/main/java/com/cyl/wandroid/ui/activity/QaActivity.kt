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
import com.cyl.wandroid.tools.start
import com.cyl.wandroid.ui.adapter.QaAdapter
import com.cyl.wandroid.viewmodel.QAViewModel
import kotlinx.android.synthetic.main.layout_swipe_recycler.*

class QaActivity : BaseRecyclerViewModelActivity<ArticleBean, QAViewModel>(), OnItemClickListener {
    private lateinit var adapter: QaAdapter

    override fun getAdapter() = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        adapter = QaAdapter()
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)
        adapter.loadMoreModule.setOnLoadMoreListener { mViewModel.loadMoreQA() }
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.refreshQA() }
    }

    override fun getViewModelClass() = QAViewModel::class.java

    override fun initView() {
        super.initView()
        setCenterText(R.string.question_answer)
    }

    override fun initData() {
        mViewModel.refreshQA()
    }

    override fun getLayoutRes() = R.layout.activity_qa
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        start(this, AgentWebActivity::class.java, Bundle().apply {
            putString(AgentWebActivity.URL, mViewModel.qaLiveData.value?.get(position)?.link)
            putBoolean(AgentWebActivity.SHOW_COLLECT_ITEM, false)
        })
    }

    override fun observe() {
        super.observe()
        mViewModel.apply {
            qaLiveData.observe(this@QaActivity, Observer {
                adapter.setList(it)
            })
        }
    }
}