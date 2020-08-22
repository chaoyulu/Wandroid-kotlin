package com.cyl.wandroid.ui.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.cyl.wandroid.R
import com.cyl.wandroid.listener.OnDatePickerSelectListener
import com.cyl.wandroid.tools.DATE_FORMAT_2
import com.cyl.wandroid.tools.millisSecondsToDateString
import java.util.*

class DatePickerView() {
    private var showType: BooleanArray = booleanArrayOf(true, true, true, false, false, false)
    private var dividerColor: Int = Color.DKGRAY
    private var contentTextSize = 20f
    private var startDate = Calendar.getInstance()
    private var endDate = Calendar.getInstance()
    private var selectDate = Calendar.getInstance()
    private var listener: OnDatePickerSelectListener? = null
    private lateinit var pickerView: TimePickerView

    private constructor(context: Context, builder: DatePickerBuilder) : this() {
        this.showType = builder.showType
        this.dividerColor = builder.divideColor
        this.contentTextSize = builder.contentFontSize
        this.startDate = builder.startDate
        this.endDate = builder.endDate
        this.selectDate = builder.selectedDate
        this.listener = builder.listener

        init(context)
    }

    fun init(context: Context) {
        val builder = TimePickerBuilder(context,
            OnTimeSelectListener { date, _ ->
                listener?.onDateSelect(millisSecondsToDateString(date.time, DATE_FORMAT_2))
            })

        builder.apply {
            setType(showType)
            isCyclic(false)
            setDate(selectDate)
            setRangDate(startDate, endDate)
            isDialog(true)
            isCenterLabel(true)
        }

        pickerView = builder.build()
        val dialog: Dialog = pickerView.dialog
        initDialog(dialog)
    }

    private fun initDialog(dialog: Dialog) {
        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
        )
        params.leftMargin = 0
        params.rightMargin = 0
        pickerView.dialogContainerLayout.layoutParams = params
        val dialogWindow = dialog.window

        dialogWindow?.let {
            it.setWindowAnimations(R.style.picker_view_slide_anim) //修改动画样式
            it.setGravity(Gravity.BOTTOM) //改成Bottom,底部显示
            it.setDimAmount(0.5f)

//            val drawable = GradientDrawable()
//            drawable.cornerRadius = 500f
//            drawable.setColor(Color.WHITE)
//            it.decorView.background = drawable
        }
    }

    fun show() {
        pickerView.show()
    }

    class DatePickerBuilder(val context: Context) {
        var showType: BooleanArray = booleanArrayOf(true, true, true, false, false, false)
        var divideColor: Int = Color.DKGRAY
        var contentFontSize = 20f
        var startDate: Calendar = Calendar.getInstance()
        var endDate: Calendar = Calendar.getInstance()
        var selectedDate: Calendar = Calendar.getInstance()
        var listener: OnDatePickerSelectListener? = null

        fun setType(type: BooleanArray) {
            showType = type
        }

        fun setDividerColor(color: Int) {
            divideColor = color
        }

        fun setContentTextSize(size: Float) {
            contentFontSize = size
        }

        fun setRangeDate(startDate: Calendar, endDate: Calendar) {
            this.startDate = startDate
            this.endDate = endDate
        }

        fun setSelectDate(selectDate: Calendar) {
            this.selectedDate = selectDate
        }

        fun setOnDateSelectListener(listener: OnDatePickerSelectListener) {
            this.listener = listener
        }

        fun build(): DatePickerView {
            return DatePickerView(context, this)
        }
    }
}