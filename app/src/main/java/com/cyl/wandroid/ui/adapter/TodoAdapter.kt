package com.cyl.wandroid.ui.adapter

import android.graphics.Color
import androidx.core.view.isVisible
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R
import com.cyl.wandroid.ext.toColor
import com.cyl.wandroid.http.bean.TodoBean
import com.cyl.wandroid.tools.DATE_FORMAT_2
import com.cyl.wandroid.tools.dateStringToDate
import com.cyl.wandroid.tools.imgTint
import com.cyl.wandroid.tools.millisSecondsToDate
import kotlinx.android.synthetic.main.item_my_todo.view.*

class TodoAdapter(private val status: Int, layoutResId: Int = R.layout.item_my_todo) :
    BaseQuickAdapter<TodoBean, BaseViewHolder>(layoutResId) {

    init {
        addChildClickViewIds(R.id.ivStatus, R.id.ivDelete)
    }

    override fun convert(holder: BaseViewHolder, item: TodoBean) {
        holder.apply {
            setText(R.id.tvTitle, item.title)
            setText(R.id.tvDesc, item.content)
            setText(R.id.tvCreateDate, context.getString(R.string.todo_date, item.dateStr))

            setText(
                R.id.tvCompletionDate,
                context.getString(R.string.completion_date, item.completeDateStr)
            )
        }

        holder.itemView.apply {
            tvDesc.isVisible = item.content.isNotEmpty()
            tvCompletionDate.isVisible = item.completeDateStr.isNotEmpty()

            val color = if (status == 0) R.color.grey_77E7E7E7.toColor(context) else Color.WHITE
            imgTint(ivStatus, color)

            if (status == 0) {
                val currentDate = millisSecondsToDate(System.currentTimeMillis(), DATE_FORMAT_2)
                val createDate = dateStringToDate(item.dateStr, DATE_FORMAT_2)
                val after = currentDate?.after(createDate)!!
                // 当前日期在创建日期之后，视为过期
                tvOverdue.isVisible = after
            }
        }
    }
}