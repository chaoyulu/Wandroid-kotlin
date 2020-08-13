package com.cyl.wandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseRecyclerViewModelFragment
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.tools.IntentTools
import com.cyl.wandroid.ui.activity.AgentWebActivity
import com.cyl.wandroid.ui.adapter.PublicAccountArticlesAdapter
import com.cyl.wandroid.viewmodel.PublicAccountArticlesViewModel
import kotlinx.android.synthetic.main.layout_swipe_recycler.*

/**
 * 公众号文章列表
 */
class PublicAccountArticlesSubFragment :
    BaseRecyclerViewModelFragment<ArticleBean, PublicAccountArticlesViewModel>(),
    OnItemClickListener {
    private lateinit var adapter: PublicAccountArticlesAdapter
    private var pubId = 0

    companion object {
        fun newFragment(id: Int): PublicAccountArticlesSubFragment {
            val bundle = Bundle()
            bundle.putInt("id", id)
            val homePublicAccountSubFragment = PublicAccountArticlesSubFragment()
            homePublicAccountSubFragment.arguments = bundle
            return homePublicAccountSubFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            pubId = it.getInt("id")
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getLayoutRes() = R.layout.layout_swipe_recycler

    override fun getAdapter(): PublicAccountArticlesAdapter = adapter
    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(mContext)
        recyclerView.layoutManager = manager
        adapter = PublicAccountArticlesAdapter()
        recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel.loadMorePublicAccountArticles(pubId)
        }
        adapter.setOnItemClickListener(this)
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.refreshPublicAccountArticles(pubId) }
    }

    override fun lazyInitData() {
        mViewModel.refreshPublicAccountArticles(pubId)
    }

    override fun getViewModelClass() = PublicAccountArticlesViewModel::class.java

    override fun observe() {
        super.observe()
        mViewModel.articles.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
        })
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        IntentTools.start(mContext, AgentWebActivity::class.java, Bundle().apply {
            putString(AgentWebActivity.URL, mViewModel.articles.value?.get(position)?.link)
        })
    }
}
