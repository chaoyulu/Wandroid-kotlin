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
import com.cyl.wandroid.viewmodel.OthersSharedViewModel
import kotlinx.android.synthetic.main.layout_swipe_recycler.*

class OthersSharedActivity : BaseRecyclerViewModelActivity<ArticleBean, OthersSharedViewModel>(),
    OnItemClickListener {

    companion object {
        const val USER_ID = "user_id"
        const val SHARED_USER = "shared_user"
    }

    private var userId: Int = 0
    private lateinit var adapter: MySharedAdapter

    override fun getAdapter() = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        adapter = MySharedAdapter()
        recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener { mViewModel.loadMoreShared(userId) }
        adapter.setOnItemClickListener(this)
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.refreshShared(userId) }
    }

    override fun getViewModelClass() = OthersSharedViewModel::class.java

    override fun initData() {
        intent?.let { it ->
            it.extras?.let {
                userId = it.getInt(USER_ID)
            }
        }
        mViewModel.refreshShared(userId)
    }

    override fun initView() {
        super.initView()
        setRightIcon(R.mipmap.icon_my_share)
        intent?.extras?.getString(SHARED_USER)?.let { setCenterText("${it}的分享") }
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
            sharedList.observe(this@OthersSharedActivity, Observer { adapter.setList(it) })
        }
    }

    override fun onRightIconClick() {
        super.onRightIconClick()
        IntentTools.start(this, MySharedActivity::class.java, needLogin = true)
    }
}