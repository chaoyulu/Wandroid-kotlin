package com.cyl.wandroid.ui.adapter

import android.graphics.Paint
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R
import com.cyl.wandroid.http.bean.NavigationBean
import com.cyl.wandroid.listener.OnTagClickListener
import kotlinx.android.synthetic.main.item_navigation_category.view.*

class NavigationAdapter(
    layoutResId: Int = R.layout.item_navigation_category, val listener: OnTagClickListener
) :
    BaseQuickAdapter<NavigationBean, BaseViewHolder>(layoutResId) {

    private lateinit var onTagClickListener: OnTagClickListener

    public fun setOnTagClickListener(onTagClickListener: OnTagClickListener) {
        this.onTagClickListener = onTagClickListener
    }

    override fun convert(holder: BaseViewHolder, item: NavigationBean) {
        holder.itemView.apply {
            llTitle.removeAllViews()
            for (i in item.articles.indices) {
                val textView = TextView(context)
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                textView.layoutParams = params
                textView.text = item.articles[i].title
                textView.setPadding(50, 40, 50, 40)
                textView.paint.flags = Paint.UNDERLINE_TEXT_FLAG
                llTitle.addView(textView)

                textView.setOnClickListener {
                    onTagClickListener.onTagClick(holder.layoutPosition, i)
                }
            }
        }
    }
}