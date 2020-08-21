package com.cyl.wandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R
import com.cyl.wandroid.ext.setCircularCorner
import com.cyl.wandroid.ext.toColor
import kotlinx.android.synthetic.main.item_home_article.view.tvAuthor
import kotlinx.android.synthetic.main.item_home_article.view.tvTitle
import kotlinx.android.synthetic.main.item_open_source.view.*

class OpenSourceAdapter(layoutResId: Int = R.layout.item_open_source) :
    BaseQuickAdapter<Triple<String, String, String>, BaseViewHolder>(layoutResId) {

    override fun convert(holder: BaseViewHolder, item: Triple<String, String, String>) {
        holder.itemView.apply {
            tvTitle.text = item.first
            tvAuthor.text = item.second
            tvLink.text = item.third

            tvAuthor.setCircularCorner(R.color.green_4CAF50.toColor(context), 5f)
        }
    }
}