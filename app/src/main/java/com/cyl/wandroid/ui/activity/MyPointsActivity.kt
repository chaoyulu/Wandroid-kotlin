package com.cyl.wandroid.ui.activity

import android.graphics.Color
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseRecyclerViewModelActivity
import com.cyl.wandroid.http.bean.PointsListBean
import com.cyl.wandroid.listener.AppbarStateChangeListener
import com.cyl.wandroid.tools.imgTint
import com.cyl.wandroid.tools.makeStatusBarTransparent
import com.cyl.wandroid.tools.start
import com.cyl.wandroid.ui.adapter.MyPointsRecordAdapter
import com.cyl.wandroid.viewmodel.MyPointsViewModel
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_my_points.*
import kotlinx.android.synthetic.main.include_points_header.*
import kotlinx.android.synthetic.main.toolbar_activity.*

class MyPointsActivity : BaseRecyclerViewModelActivity<PointsListBean, MyPointsViewModel>() {

    override fun getViewModelClass() = MyPointsViewModel::class.java

    private lateinit var adapter: MyPointsRecordAdapter

    override fun initData() {
        mViewModel.getMyPointsInfo()
    }

    override fun getLayoutRes() = R.layout.activity_my_points

    override fun initView() {
        super.initView()
        setCenterText(R.string.my_points)
        makeStatusBarTransparent(this)
        relToolbar.setBackgroundColor(Color.TRANSPARENT)
        appbarListener()
        toolbarMarginTop()
        setRightIcon(R.mipmap.icon_points_rank)
    }

    override fun observe() {
        super.observe()
        mViewModel.apply {
            pointsInfo.observe(this@MyPointsActivity, Observer {
                tvPointsCount.text = "${it.coinCount}"
                tvLevel.text = getString(R.string.points_level, it.level)
                tvRank.text = getString(R.string.points_rank, it.rank)
            })
            pointsList.observe(this@MyPointsActivity, Observer {
                adapter.setList(it)
            })
        }
    }

    override fun getAdapter() = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        adapter = MyPointsRecordAdapter()
        recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel.loadMorePointsList()
        }
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.getMyPointsInfo() }
    }

    private fun appbarListener() {
        appBar.addOnOffsetChangedListener(object : AppbarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                tvCenter.isVisible = state != State.EXPANDED
                imgTint(ivBack, if (state == State.EXPANDED) Color.WHITE else Color.BLACK)
                imgTint(ivRight, if (state == State.EXPANDED) Color.WHITE else Color.BLACK)
            }
        })
    }

    private fun toolbarMarginTop() {
        val params = toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams
        params.topMargin = ImmersionBar.getStatusBarHeight(this)
        toolbar.layoutParams = params
    }

    override fun onRightIconClick() {
        super.onRightIconClick()
        start(this, PointsRankActivity::class.java)
    }
}