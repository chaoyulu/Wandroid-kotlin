package com.cyl.wandroid.ui.activity

import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseRecyclerViewModelActivity
import com.cyl.wandroid.http.bean.PointRankBean
import com.cyl.wandroid.ui.adapter.PointsRankAdapter
import com.cyl.wandroid.viewmodel.PointsRankViewModel
import kotlinx.android.synthetic.main.header_points_rank.view.*
import kotlinx.android.synthetic.main.layout_swipe_recycler.*

class PointsRankActivity : BaseRecyclerViewModelActivity<PointRankBean, PointsRankViewModel>() {
    private lateinit var adapter: PointsRankAdapter

    override fun getAdapter() = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout
    private lateinit var headerView: View

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        adapter = PointsRankAdapter()
        recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener { mViewModel.loadMorePointsList() }
        headerView = LayoutInflater.from(this).inflate(R.layout.header_points_rank, null)
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            mViewModel.refreshPointsRank()
        }
    }

    override fun getViewModelClass() = PointsRankViewModel::class.java

    override fun initData() {
        mViewModel.refreshPointsRank()
    }

    override fun initView() {
        super.initView()
        setCenterText(R.string.points_rank_title)
    }

    override fun getLayoutRes() = R.layout.activity_points_rank

    override fun observe() {
        super.observe()
        mViewModel.apply {
            pointsRankList.observe(this@PointsRankActivity, Observer {
                adapter.removeAllHeaderView()
                adapter.addHeaderView(headerView)
                adapter.setList(it)

                setTopThree(it)
            })
        }
    }

    private fun setTopThree(it: MutableList<PointRankBean>) {
        val firstRangeBean = it[0]
        val secondRangeBean = it[1]
        val thirdRangeBean = it[2]

        headerView.apply {
            hv_first.text = firstRangeBean.username
            tvPointCountFirst.text = firstRangeBean.coinCount.toString()
            hv_second.text = secondRangeBean.username
            tvPointCountSecond.text = secondRangeBean.coinCount.toString()
            hv_third.text = thirdRangeBean.username
            tvPointCountThird.text = thirdRangeBean.coinCount.toString()
        }
    }
}