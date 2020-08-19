package com.cyl.wandroid.ui.activity

import android.graphics.Color
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseViewModelActivity
import com.cyl.wandroid.common.bus.ADD_TODO_SUCCESS
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.UPDATE_TODO_SUCCESS
import com.cyl.wandroid.http.bean.TodoBean
import com.cyl.wandroid.listener.OnDatePickerSelectListener
import com.cyl.wandroid.tools.dateStringToCalendar
import com.cyl.wandroid.tools.imgTint
import com.cyl.wandroid.tools.makeStatusBarTransparent
import com.cyl.wandroid.tools.showError
import com.cyl.wandroid.ui.dialog.LoadingDialog
import com.cyl.wandroid.ui.widget.DatePickerView
import com.cyl.wandroid.viewmodel.AddUpdateMyTodoViewModel
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.activity_add_or_update_todo.*
import kotlinx.android.synthetic.main.toolbar_activity.*
import java.util.*

class AddOrUpdateTodoActivity : BaseViewModelActivity<AddUpdateMyTodoViewModel>(),
    OnDatePickerSelectListener {
    private var action = ACTION_ADD
    private var todoBean: TodoBean? = null
    private var loadingDialog: LoadingDialog? = null

    companion object {
        const val TODO_BEAN = "todo_bean"
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

        selectDate()
    }

    private fun selectDate() {
        val startDate = Calendar.getInstance()
        val endDate = Calendar.getInstance()
        endDate.set(2050, 11, 31)

        tvDate.setOnClickListener {
            val selectDateString = tvDate.text.toString()
            val selectDate =
                if (selectDateString.isEmpty()) Calendar.getInstance() else dateStringToCalendar(
                    tvDate.text.toString()
                )
            DatePickerView.DatePickerBuilder(this).apply {
                setSelectDate(selectDate)
                setRangeDate(startDate, dateStringToCalendar("2050-12-31"))
                setOnDateSelectListener(this@AddOrUpdateTodoActivity)
            }.build().show()
        }
    }

    override fun onDateSelect(date: String) {
        tvDate.text = date
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

            addTodoLiveData.observe(this@AddOrUpdateTodoActivity, Observer {
                Bus.post(ADD_TODO_SUCCESS, it)
                finish()
            })
            updateTodoLiveData.observe(this@AddOrUpdateTodoActivity, Observer {
                Bus.post(UPDATE_TODO_SUCCESS, it)
                finish()
            })
        }
    }
}