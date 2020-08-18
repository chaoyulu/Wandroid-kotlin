package com.cyl.wandroid.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseRecyclerViewModelFragment
import com.cyl.wandroid.http.bean.TodoBean
import com.cyl.wandroid.tools.start
import com.cyl.wandroid.ui.activity.AddOrUpdateTodoActivity
import com.cyl.wandroid.ui.adapter.TodoAdapter
import com.cyl.wandroid.ui.widget.SpaceItemDecoration
import com.cyl.wandroid.viewmodel.MyTodoViewModel
import kotlinx.android.synthetic.main.layout_swipe_recycler.*

class MyTodoFragment : BaseRecyclerViewModelFragment<TodoBean, MyTodoViewModel>(),
    OnItemChildClickListener, OnItemClickListener {
    private var status = 0
    private lateinit var adapter: TodoAdapter

    override fun getLayoutRes() = R.layout.layout_swipe_recycler

    override fun lazyInitData() {
        mViewModel.refreshMyTodo(status)
    }

    companion object {
        const val STATUS = "status"
        const val STATUS_TO_DO = 0 // 待办
        const val STATUS_DONE = 1 // 已办
    }

    override fun getAdapter() = adapter

    override fun getSwipeRefreshLayout(): SwipeRefreshLayout = swipeRefreshLayout

    override fun initRecyclerView() {
        status = arguments?.getInt(STATUS, 0)!!
        val manager = LinearLayoutManager(mContext)
        recyclerView.layoutManager = manager
        adapter = TodoAdapter(status)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(SpaceItemDecoration(50))
        adapter.setOnItemChildClickListener(this)
        adapter.setOnItemClickListener(this)
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.refreshMyTodo(status) }
    }

    override fun getViewModelClass() = MyTodoViewModel::class.java

    override fun observe() {
        super.observe()
        mViewModel.todoLiveData.observe(viewLifecycleOwner, Observer {
            adapter.setList(it)
        })
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        start(mContext, AddOrUpdateTodoActivity::class.java, Bundle().apply {
            putParcelable(
                AddOrUpdateTodoActivity.TODO_BEAN, mViewModel.todoLiveData.value?.get(position)
            )
            putInt(AddOrUpdateTodoActivity.ACTION, AddOrUpdateTodoActivity.ACTION_UPDATE)
        })
    }
}