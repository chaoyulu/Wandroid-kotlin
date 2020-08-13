package com.cyl.wandroid.ui.fragment

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseRecyclerViewModelFragment
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.SCROLL_HOME_PUBLIC_ACCOUNT_POSITION
import com.cyl.wandroid.http.bean.PublicAccountBean
import com.cyl.wandroid.tools.IntentTools
import com.cyl.wandroid.ui.activity.PublicAccountArticlesActivity
import com.cyl.wandroid.ui.adapter.HomePublicAccountAdapter
import com.cyl.wandroid.viewmodel.HomePublicAccountViewModel
import kotlinx.android.synthetic.main.layout_swipe_recycler.*

/**
 * 首页公众号Tab
 */
class HomePublicAccountFragment :
    BaseRecyclerViewModelFragment<PublicAccountBean, HomePublicAccountViewModel>() {
    override fun getLayoutRes() = R.layout.layout_swipe_recycler

    private lateinit var adapter: HomePublicAccountAdapter

    override fun lazyInitData() {
        mViewModel.getHomePublicAccountList()
    }

    override fun observe() {
        super.observe()
        eventBusObserve()
        mViewModel.accounts.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
        })
    }

    private fun eventBusObserve() {
        Bus.observe<Int>(SCROLL_HOME_PUBLIC_ACCOUNT_POSITION, viewLifecycleOwner, observer = {
            recyclerView.smoothScrollToPosition(it)
        })
    }

    override fun getViewModelClass() = HomePublicAccountViewModel::class.java
    override fun getAdapter() = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(mContext)
        recyclerView.layoutManager = manager
        adapter = HomePublicAccountAdapter()
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener { _, _, position ->
            val accounts = mViewModel.accounts.value
            IntentTools.start(
                mContext, PublicAccountArticlesActivity::class.java,
                Bundle().apply {
                    putInt("position", position)
                    putParcelableArrayList("accounts", accounts)
                }
            )
        }
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.getHomePublicAccountList() }
    }
}
