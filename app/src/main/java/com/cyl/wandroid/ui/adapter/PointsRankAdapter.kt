package com.cyl.wandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R
import com.cyl.wandroid.http.bean.PointRankBean

class PointsRankAdapter(layoutResId: Int = R.layout.item_points_rank) :
    BaseQuickAdapter<PointRankBean, BaseViewHolder>(layoutResId), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: PointRankBean) {
        holder.apply {
            setText(R.id.tvName, item.username)
            setText(R.id.tvRank, item.rank)
            setText(R.id.tvLevel, item.level.toString())
            setText(R.id.tvCoinCount, "${item.coinCount}")
        }
    }
}