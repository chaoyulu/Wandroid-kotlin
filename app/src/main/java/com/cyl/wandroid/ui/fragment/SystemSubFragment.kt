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
import com.cyl.wandroid.http.bean.SystemCategoryBean
import com.cyl.wandroid.tools.IntentTools
import com.cyl.wandroid.ui.activity.AgentWebActivity
import com.cyl.wandroid.ui.adapter.PublicAccountArticlesAdapter
import com.cyl.wandroid.viewmodel.SystemArticlesViewModel
import kotlinx.android.synthetic.main.layout_swipe_recycler.*

/**
 * 体系文章列表
 */
class SystemSubFragment : BaseRecyclerViewModelFragment<ArticleBean, SystemArticlesViewModel>(),
    OnItemClickListener {
    private lateinit var bean: SystemCategoryBean
    private lateinit var adapter: PublicAccountArticlesAdapter

    companion object {
        fun newFragment(bean: SystemCategoryBean): SystemSubFragment {
            val bundle = Bundle()
            bundle.putParcelable("bean", bean)
            val systemSubFragment = SystemSubFragment()
            systemSubFragment.arguments = bundle
            return systemSubFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bean = arguments?.getParcelable("bean")!!
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getLayoutRes() = R.layout.layout_swipe_recycler

    override fun lazyInitData() {
        mViewModel.refreshSystemArticles(bean.id)
    }

    override fun getAdapter(): PublicAccountArticlesAdapter = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(mContext)
        recyclerView.layoutManager = manager
        adapter = PublicAccountArticlesAdapter()
        recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener { mViewModel.loadMoreSystemArticles(bean.id) }
        adapter.setOnItemClickListener(this)
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.refreshSystemArticles(bean.id) }
    }

    override fun getViewModelClass() = SystemArticlesViewModel::class.java

    override fun observe() {
        super.observe()
        mViewModel.apply {
            articles.observe(viewLifecycleOwner, Observer { adapter.setList(it) })
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        IntentTools.start(mContext, AgentWebActivity::class.java, Bundle().apply {
            putString(AgentWebActivity.URL, mViewModel.articles.value?.get(position)?.link)
        })
    }
}
