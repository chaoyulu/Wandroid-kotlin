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
import com.cyl.wandroid.common.bus.ADD_TODO_SUCCESS
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.UPDATE_TODO_SUCCESS
import com.cyl.wandroid.http.bean.TodoBean
import com.cyl.wandroid.tools.checkLoginThenAction
import com.cyl.wandroid.tools.start
import com.cyl.wandroid.ui.activity.AddOrUpdateTodoActivity
import com.cyl.wandroid.ui.adapter.TodoAdapter
import com.cyl.wandroid.ui.dialog.LoadingDialog
import com.cyl.wandroid.ui.dialog.OperateTipDialog
import com.cyl.wandroid.ui.widget.EmptyView
import com.cyl.wandroid.ui.widget.SpaceItemDecoration
import com.cyl.wandroid.viewmodel.MyTodoViewModel
import kotlinx.android.synthetic.main.layout_swipe_recycler.*

class MyTodoFragment : BaseRecyclerViewModelFragment<TodoBean, MyTodoViewModel>(),
    OnItemChildClickListener, OnItemClickListener {
    private var status = 0
    private lateinit var adapter: TodoAdapter
    private var loadingDialog: LoadingDialog? = null

    override fun getLayoutRes() = R.layout.layout_swipe_recycler

    override fun lazyInitData() {
        mViewModel.refreshMyTodo(status)
        busObserve()
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
        adapter.loadMoreModule.setOnLoadMoreListener {
            mViewModel.loadMoreMyTodo(status)
        }
        val emptyView = EmptyView(mContext)
        emptyView.setEmptyInfo(icon = R.mipmap.icon_no_data2, textColor = R.color.white)
        adapter.setEmptyView(emptyView)
    }

    override fun initRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener { mViewModel.refreshMyTodo(status) }
    }

    override fun getViewModelClass() = MyTodoViewModel::class.java

    override fun observe() {
        super.observe()
        mViewModel.apply {
            todoLiveData.observe(viewLifecycleOwner, Observer {
                adapter.setList(it)
            })

            requestStatusLiveData.observe(viewLifecycleOwner, Observer {
                if (it) loadingDialog?.show() else loadingDialog?.dismiss()
            })

            deleteTodoLiveData.observe(viewLifecycleOwner, Observer {
                if (it != -1) adapter.removeAt(it)
            })

            updateTodoLiveData.observe(viewLifecycleOwner, Observer {
                if (it.first != -1) adapter.removeAt(it.first)
                // 总共2个Fragment，状态改变成功后，当前Fragment删除该条TODO，另一Fragment增加该条TODO
                val fragments = activity?.supportFragmentManager?.fragments
                val fragment =
                    (fragments?.get(if (status == STATUS_TO_DO) 2 else 1)) as MyTodoFragment
                val list = fragment.mViewModel.todoLiveData.value
                list?.add(0, it.second)
                fragment.mViewModel.todoLiveData.value = list
            })
        }
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (!checkLoginThenAction(mContext)) {
            return
        }

        if (loadingDialog == null) loadingDialog = LoadingDialog(mContext)
        if (view.id == R.id.ivStatus) {
            mViewModel.todoLiveData.value?.get(position)?.id?.let {
                mViewModel.updateMyTodoStatus(
                    it, if (status == STATUS_TO_DO) STATUS_DONE else STATUS_TO_DO
                )
                loadingDialog?.show()
            }
        } else if (view.id == R.id.ivDelete) {
            OperateTipDialog(mContext, desc = R.string.is_delete_the_todo, listener = object :
                OperateTipDialog.OnTipDialogClickListener {
                override fun onCancel() {
                }

                override fun onConfirm() {
                    mViewModel.todoLiveData.value?.get(position)?.id?.let {
                        mViewModel.deleteMyTodo(it)
                        loadingDialog?.show()
                    }
                }
            }).show()
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        if (status == STATUS_TO_DO) {
            start(mContext, AddOrUpdateTodoActivity::class.java, Bundle().apply {
                putParcelable(
                    AddOrUpdateTodoActivity.TODO_BEAN, mViewModel.todoLiveData.value?.get(position)
                )
                putInt(AddOrUpdateTodoActivity.ACTION, AddOrUpdateTodoActivity.ACTION_UPDATE)
            })
        }
    }

    private fun busObserve() {
        Bus.observe<TodoBean>(ADD_TODO_SUCCESS, viewLifecycleOwner, observer = {
            mViewModel.addTodoSuccess(it)
        })
        Bus.observe<TodoBean>(UPDATE_TODO_SUCCESS, viewLifecycleOwner, observer = {
            mViewModel.updateTodoSuccess(it)
        })
    }
}