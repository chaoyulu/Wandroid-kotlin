package com.cyl.wandroid.ui.fragment

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseRecyclerViewModelFragment
import com.cyl.wandroid.http.bean.SystemCategoryBean
import com.cyl.wandroid.listener.OnTagClickListener
import com.cyl.wandroid.tools.IntentTools
import com.cyl.wandroid.tools.showError
import com.cyl.wandroid.ui.activity.SystemDetailActivity
import com.cyl.wandroid.ui.adapter.SystemCategoryAdapter
import com.cyl.wandroid.ui.widget.LocateTagViewDialog
import com.cyl.wandroid.ui.widget.SmoothTopScroller
import com.cyl.wandroid.viewmodel.SystemCategoryViewModel
import kotlinx.android.synthetic.main.fragment_home_newest_article.*
import kotlinx.android.synthetic.main.toolbar_fragment.*

/**
 * 体系
 */
class SystemFragment :
    BaseRecyclerViewModelFragment<SystemCategoryBean, SystemCategoryViewModel>(),
    OnTagClickListener {
    private lateinit var adapter: SystemCategoryAdapter
    override fun getLayoutRes() = R.layout.fragment_system

    override fun lazyInitData() {
        mViewModel.getSystemCategory()
    }

    override fun getAdapter(): BaseQuickAdapter<SystemCategoryBean, BaseViewHolder> = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout

    override fun initView() {
        super.initView()
        setCenterText(R.string.home_system)
        setRightIcon(R.mipmap.icon_system_locate)
    }

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(mContext)
        recyclerView.layoutManager = manager
        adapter = SystemCategoryAdapter(listener = this)
        recyclerView.adapter = adapter
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.getSystemCategory() }
    }

    override fun getViewModelClass() = SystemCategoryViewModel::class.java

    override fun observe() {
        super.observe()
        mViewModel.apply {
            categories.observe(viewLifecycleOwner, Observer {
                adapter.setList(it)
                ivRight.isVisible = !categories.value.isNullOrEmpty()
            })
        }
    }

    override fun onTagClick(itemPosition: Int, tagPosition: Int) {
        IntentTools.start(mContext, SystemDetailActivity::class.java, Bundle().apply {
            putInt("tagPosition", tagPosition)
            putParcelable("category", mViewModel.categories.value?.get(itemPosition))
        })
    }

    override fun onRightIconClick() {
        super.onRightIconClick()
        val categories: List<SystemCategoryBean>? = mViewModel.categories.value
        if (categories.isNullOrEmpty()) {
            // 没有数据
            showError(R.string.no_valid_data)
        } else {
            val titles = getTitles(categories)
            LocateTagViewDialog(mContext, titles, object : OnTagClickListener {
                override fun onTagClick(itemPosition: Int, tagPosition: Int) {
                    val scroller = SmoothTopScroller(mContext)
                    scroller.targetPosition = itemPosition
                    recyclerView.layoutManager?.startSmoothScroll(scroller)
                }
            }).show()
        }
    }

    private fun getTitles(categories: List<SystemCategoryBean>?): List<String> {
        if (categories.isNullOrEmpty()) return emptyList()
        val titles = mutableListOf<String>()
        categories.forEach {
            titles.add(it.name)
        }
        return titles
    }
}
