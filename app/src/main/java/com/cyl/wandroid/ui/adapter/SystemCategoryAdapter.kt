package com.cyl.wandroid.ui.adapter

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R
import com.cyl.wandroid.http.bean.SystemCategoryBean
import com.cyl.wandroid.listener.OnTagClickListener
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.item_system_category.view.*

class SystemCategoryAdapter(
    layoutResId: Int = R.layout.item_system_category, private val listener: OnTagClickListener
) :
    BaseQuickAdapter<SystemCategoryBean, BaseViewHolder>(layoutResId) {

    val tagBgColors = listOf(
        R.color.yellow_77F8D073,
        R.color.solid_purple,
        R.color.blue_772196F3,
        R.color.solid_red,
        R.color.solid_green
    )
    val textColors = listOf(
        R.color.yellow_F8D073,
        R.color.purple_673AB7,
        R.color.blue_3691FF,
        R.color.red_FF0000,
        R.color.green_4CAF50
    )


    override fun convert(holder: BaseViewHolder, item: SystemCategoryBean) {
        holder.itemView.apply {
            tvTitle.text = item.name
            val textColor = ContextCompat.getColor(
                context, textColors[holder.layoutPosition % tagBgColors.size]
            )
            tvTitle.setTextColor(textColor)
            val tagAdapter = object : TagAdapter<SystemCategoryBean>(item.children) {
                override fun getView(
                    parent: FlowLayout?,
                    position: Int,
                    t: SystemCategoryBean?
                ): View {
                    val tv = LayoutInflater.from(context)
                        .inflate(R.layout.tag_view, tagFlowLayout, false) as TextView
                    tv.text = t?.name
                    val bgColor =
                        ContextCompat.getColor(
                            context,
                            tagBgColors[holder.layoutPosition % tagBgColors.size]
                        )
                    val tagTextColor =
                        ContextCompat.getColor(
                            context,
                            textColors[holder.layoutPosition % textColors.size]
                        )
                    tv.setTextColor(tagTextColor)

                    val gradientDrawable = GradientDrawable()
                    gradientDrawable.setColor(bgColor)
                    gradientDrawable.cornerRadius = 20f
                    tv.background = gradientDrawable
                    return tv
                }
            }
            tagFlowLayout.adapter = tagAdapter
            tagFlowLayout.setOnTagClickListener { _, position, _ ->
                listener.onTagClick(holder.layoutPosition, position)
                return@setOnTagClickListener true
            }
        }
    }
}