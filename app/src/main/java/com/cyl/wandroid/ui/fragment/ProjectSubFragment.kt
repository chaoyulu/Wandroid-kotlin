package com.cyl.wandroid.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseRecyclerViewModelFragment
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.ProjectCategoryBean
import com.cyl.wandroid.tools.checkLoginThenAction
import com.cyl.wandroid.ui.adapter.HomeProjectAdapter
import com.cyl.wandroid.viewmodel.ProjectArticlesViewModel
import kotlinx.android.synthetic.main.layout_swipe_recycler.*

class ProjectSubFragment : BaseRecyclerViewModelFragment<ArticleBean, ProjectArticlesViewModel>(),
    OnItemClickListener, OnItemChildClickListener {
    private lateinit var adapter: HomeProjectAdapter
    private lateinit var bean: ProjectCategoryBean

    companion object {
        fun newFragment(bean: ProjectCategoryBean): ProjectSubFragment {
            val bundle = Bundle()
            bundle.putParcelable("bean", bean)
            val projectSubFragment = ProjectSubFragment()
            projectSubFragment.arguments = bundle
            return projectSubFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        arguments?.let {
            bean = it.getParcelable("bean")!!
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getLayoutRes() = R.layout.layout_swipe_recycler

    override fun lazyInitData() {
        mViewModel.refreshProjectArticles(bean.id)
    }

    override fun getAdapter(): BaseQuickAdapter<ArticleBean, BaseViewHolder> = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(mContext)
        recyclerView.layoutManager = manager
        adapter = HomeProjectAdapter()
        recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener { mViewModel.loadMoreProjectArticles(bean.id) }
        adapter.setOnItemClickListener(this)
        adapter.setOnItemChildClickListener(this)
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.refreshProjectArticles(bean.id) }
    }

    override fun getViewModelClass() = ProjectArticlesViewModel::class.java

    override fun getViewModelArticles(): MutableLiveData<MutableList<ArticleBean>> {
        return mViewModel.articles
    }

    override fun observe() {
        super.observe()
        mViewModel.apply {
            articles.observe(viewLifecycleOwner, Observer {
                adapter.setList(it)
            })
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        onItemClickToAgentWeb(position)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (view.id == R.id.ivCollection && checkLoginThenAction(mContext)) {
            // 收藏
            collectItemChildClick(position)
        }
    }
}
