package com.cyl.wandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R
import com.cyl.wandroid.http.bean.PointsListBean
import com.cyl.wandroid.tools.millisSecondsToDateString

class MyPointsRecordAdapter(layoutResId: Int = R.layout.item_my_points_record) :
    BaseQuickAdapter<PointsListBean, BaseViewHolder>(layoutResId), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: PointsListBean) {
        holder.apply {
            setText(R.id.tvReason, item.reason)
            setText(R.id.tvDate, millisSecondsToDateString(item.date))
            setText(R.id.tvCoinCount, "+${item.coinCount}")
        }
    }
}