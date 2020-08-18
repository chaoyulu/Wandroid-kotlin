package com.cyl.wandroid.ui.activity

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseRecyclerViewModelActivity
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.REFRESH_ADD_SHARE_SUCCESS
import com.cyl.wandroid.common.code.REQUEST_CODE_ADD_SHARE
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.listener.OnSharedChildClickListener
import com.cyl.wandroid.tools.checkLoginThenAction
import com.cyl.wandroid.tools.start
import com.cyl.wandroid.ui.adapter.MySharedAdapter
import com.cyl.wandroid.ui.dialog.LoadingDialog
import com.cyl.wandroid.ui.widget.EmptyView
import com.cyl.wandroid.viewmodel.MySharedViewModel
import kotlinx.android.synthetic.main.layout_swipe_recycler.*

class MySharedActivity : BaseRecyclerViewModelActivity<ArticleBean, MySharedViewModel>(),
    OnItemClickListener, OnItemChildClickListener, OnSharedChildClickListener {
    private lateinit var adapter: MySharedAdapter

    override fun getAdapter() = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout
    private var loadingDialog: LoadingDialog? = null

    override fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        adapter = MySharedAdapter()
        recyclerView.adapter = adapter
        adapter.loadMoreModule.setOnLoadMoreListener { mViewModel.loadMoreShared() }
        val emptyView = EmptyView(this)
        emptyView.setEmptyInfo(textRes = R.string.no_share)
        adapter.setEmptyView(emptyView)
        adapter.setOnItemClickListener(this)
        adapter.setOnItemChildClickListener(this)
        adapter.setOnSharedChildClickListener(this)
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.refreshShared() }
    }

    override fun getViewModelClass() = MySharedViewModel::class.java

    override fun getViewModelArticles(): MutableLiveData<MutableList<ArticleBean>> {
        return mViewModel.sharedList
    }

    override fun initData() {
        mViewModel.refreshShared()
    }

    override fun initView() {
        super.initView()
        setCenterText(R.string.my_shared)
        setRightIcon(R.mipmap.icon_add)
    }

    override fun getLayoutRes() = R.layout.activity_my_shared

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        onItemClickToAgentWeb(position)
    }

    override fun observe() {
        super.observe()
        mViewModel.apply {
            sharedList.observe(this@MySharedActivity, Observer { adapter.setList(it) })
            deleteShareLiveData.observe(this@MySharedActivity, Observer {
                if (it.second) {
                    loadingDialog?.show()
                } else {
                    loadingDialog?.dismiss()
                    if (it.first != -1) adapter.removeAt(it.first)
                }

            })
        }
    }

    override fun collectBusObserve() {
        super.collectBusObserve()
        Bus.observe<Int>(REFRESH_ADD_SHARE_SUCCESS, this, observer = {
            mViewModel.refreshShared()
        })
    }

    override fun onRightIconClick() {
        super.onRightIconClick()
        start(
            this,
            AddShareActivity::class.java,
            needLogin = true,
            needResult = true,
            requestCode = REQUEST_CODE_ADD_SHARE
        )
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (!checkLoginThenAction(this)) {
            return
        }
        if (view.id == R.id.ivCollection) {
            collectItemChildClick(position, isFromCollectActivity = false)
        }
    }

    override fun onWholeItemClick(position: Int) {
        onItemClickToAgentWeb(position)
    }

    override fun onDeleteClick(position: Int) {
        if (loadingDialog == null) loadingDialog = LoadingDialog(this)
        loadingDialog?.setDesc(R.string.deleting)
        mViewModel.sharedList.value?.get(position)?.id?.let { mViewModel.deleteMyShare(it) }
    }
}