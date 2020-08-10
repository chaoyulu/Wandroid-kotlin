package com.cyl.wandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseRecyclerViewModelFragment
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.JUMP_TO_PROJECT_FRAGMENT
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.HomeBannerBean
import com.cyl.wandroid.tools.IntentTools
import com.cyl.wandroid.ui.activity.AgentWebActivity
import com.cyl.wandroid.ui.activity.PublicAccountContainerActivity
import com.cyl.wandroid.ui.adapter.HomeArticleAdapter
import com.cyl.wandroid.ui.adapter.HomeBannerAdapter
import com.cyl.wandroid.viewmodel.HomeNewestArticleViewModel
import com.youth.banner.Banner
import com.youth.banner.config.IndicatorConfig
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.fragment_home_newest_article.*
import kotlinx.android.synthetic.main.header_view_fragment_home.view.*

/**
 * 首页最新文章Tab
 */
class HomeNewestArticleFragment :
    BaseRecyclerViewModelFragment<ArticleBean, HomeNewestArticleViewModel>(), OnItemClickListener {
    private lateinit var adapter: HomeArticleAdapter
    override fun getLayoutRes() = R.layout.fragment_home_newest_article
    private lateinit var banner: Banner<HomeBannerBean, HomeBannerAdapter>

    override fun lazyInitData() {
        mViewModel.refreshHomeNewestArticle()
        mViewModel.getHomeBanner()
    }

    override fun initView() {
        super.initView()
        initBanner()
    }

    private fun initBanner() {
        banner.apply {
            banner.addBannerLifecycleObserver(this@HomeNewestArticleFragment)
            adapter = HomeBannerAdapter(mViewModel.banners.value)
            indicator = CircleIndicator(activity)
            setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
        }
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.refreshHomeNewestArticle() }
    }

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(mContext)
        recyclerView.layoutManager = manager
        adapter = HomeArticleAdapter()
        adapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel.loadMoreArticles()
        }

        val headerView =
            LayoutInflater.from(mContext).inflate(R.layout.header_view_fragment_home, null)
        setHeaderMenuClick(headerView)
        adapter.addHeaderView(headerView)
        banner = headerView.findViewById(R.id.banner)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                mContext, DividerItemDecoration.VERTICAL
            )
        )
        adapter.setOnItemClickListener(this)
    }

    private fun setHeaderMenuClick(headerView: View) {
        headerView.hivProject.setOnClickListener {
            Bus.post(JUMP_TO_PROJECT_FRAGMENT, R.id.project)
        }

        headerView.hivPublicAccount.setOnClickListener {
            IntentTools.start(mContext, PublicAccountContainerActivity::class.java, null)
        }
    }

    override fun observe() {
        super.observe()
        mViewModel.apply {
            articles.observe(viewLifecycleOwner, Observer {
                adapter.setList(it)
            })

            banners.observe(viewLifecycleOwner, Observer {
                setBanners(it)
            })
        }
    }

    private fun setBanners(it: List<HomeBannerBean>) {
        banner.apply {
            adapter.apply {
                setDatas(it)
                notifyDataSetChanged()
            }
            setCurrentItem(1, false)
            setIndicatorPageChange()
            start()
        }
    }

    override fun getViewModelClass() = HomeNewestArticleViewModel::class.java

    override fun getAdapter() = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        IntentTools.start(mContext, AgentWebActivity::class.java, Bundle().apply {
            putString(AgentWebActivity.URL, mViewModel.articles.value?.get(position)?.link)
        })
    }
}
