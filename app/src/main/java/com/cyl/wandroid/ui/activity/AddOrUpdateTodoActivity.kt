package com.cyl.wandroid.ui.activity

import android.graphics.Color
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseViewModelActivity
import com.cyl.wandroid.http.bean.TodoBean
import com.cyl.wandroid.tools.imgTint
import com.cyl.wandroid.tools.makeStatusBarTransparent
import com.cyl.wandroid.tools.showError
import com.cyl.wandroid.ui.dialog.LoadingDialog
import com.cyl.wandroid.viewmodel.AddUpdateMyTodoViewModel
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_add_or_update_todo.*
import kotlinx.android.synthetic.main.toolbar_activity.*

class AddOrUpdateTodoActivity : BaseViewModelActivity<AddUpdateMyTodoViewModel>() {
    private var action = ACTION_ADD
    private var todoBean: TodoBean? = null
    private var loadingDialog: LoadingDialog? = null

    companion object {
        const val TODO_BEAN = "todo_bean"
        const val TYPE = "type"
        const val ACTION = "action" // 标识是新增还是修改

        const val ACTION_ADD = 0
        const val ACTION_UPDATE = 1
    }

    override fun getViewModelClass() = AddUpdateMyTodoViewModel::class.java

    override fun initData() {
        if (action == ACTION_ADD) {
            actionAdd()
        } else {
            actionUpdate()
        }
    }

    override fun initView() {
        action = intent?.extras?.getInt(ACTION, ACTION_ADD)!!
        makeStatusBarTransparent(this)
        toolbar.setBackgroundColor(Color.TRANSPARENT)
        toolbarMarginTop()
        container.setBackgroundResource(R.drawable.shape_register_gradient)
        imgTint(ivBack, Color.WHITE)
        setCenterTextColor(Color.WHITE)
        setRightIcon(R.mipmap.icon_submit)
        imgTint(ivRight, Color.WHITE)
    }

    override fun getLayoutRes() = R.layout.activity_add_or_update_todo

    private fun toolbarMarginTop() {
        val params = toolbar.layoutParams as LinearLayout.LayoutParams
        params.topMargin = ImmersionBar.getStatusBarHeight(this)
        toolbar.layoutParams = params
    }

    private fun actionAdd() {
        setCenterText(R.string.add_todo)
    }

    private fun actionUpdate() {
        setCenterText(R.string.update_todo)
        intent?.apply {
            extras?.apply {
                todoBean = getParcelable(TODO_BEAN)
                etTitle.setText(todoBean?.title)
                etDetail.setText(todoBean?.content)
                tvDate.text = todoBean?.dateStr
            }
        }
    }

    override fun onRightIconClick() {
        super.onRightIconClick()
        val title = etTitle.text.toString()
        val content = etDetail.text.toString()
        val date = tvDate.text.toString()

        if (title.isEmpty()) {
            showError("标题为必填项")
            return
        }

        if (content.isEmpty()) {
            showError("详情为必填项")
            return
        }

        if (date.isEmpty()) {
            showError("请先选择日期")
            return
        }

        if (loadingDialog == null) loadingDialog = LoadingDialog(this)
        if (action == ACTION_ADD) {
            mViewModel.addMyTodo(title, content, date)
        } else {
            todoBean?.let {
                mViewModel.updateMyTodo(
                    it.id, title, content, date, it.status, it.type, it.priority
                )
            }
        }
    }

    override fun observe() {
        mViewModel.apply {
            requestStatusLiveData.observe(this@AddOrUpdateTodoActivity, Observer {
                if (it) loadingDialog?.show() else loadingDialog?.dismiss()
            })

            addTodoLiveData.observe(this@AddOrUpdateTodoActivity, Observer { })
            updateTodoLiveData.observe(this@AddOrUpdateTodoActivity, Observer { })
        }
    }
}