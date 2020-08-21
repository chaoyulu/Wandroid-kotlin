package com.cyl.wandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R
import com.cyl.wandroid.ext.setCircularCorner
import com.cyl.wandroid.ext.toColor
import com.cyl.wandroid.http.bean.PublicAccountBean
import kotlinx.android.synthetic.main.item_home_public_account.view.*

class HomePublicAccountAdapter(layoutResId: Int = R.layout.item_home_public_account) :
    BaseQuickAdapter<PublicAccountBean, BaseViewHolder>(layoutResId) {

    private val colors = arrayOf(
        R.color.solid_red,
        R.color.solid_orange,
        R.color.solid_green,
        R.color.solid_cyan,
        R.color.solid_yellow,
        R.color.solid_blue,
        R.color.solid_purple
    )

    override fun convert(holder: BaseViewHolder, item: PublicAccountBean) {
        holder.itemView.apply {
            tvTitle.text = item.name
            tvTitle.setCircularCorner(
                colors[holder.adapterPosition % colors.size].toColor(context),
                50f
            )
        }
    }
}