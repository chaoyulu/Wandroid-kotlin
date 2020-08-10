package com.cyl.wandroid.ui.adapter

import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R

class LocateRecyclerViewAdapter(
    private val columns: Int,
    layoutResId: Int = R.layout.item_dialog_locate_recycler_view
) :
    BaseQuickAdapter<String, BaseViewHolder>(layoutResId) {
    override fun convert(holder: BaseViewHolder, item: String) {
        val stv: SuperTextView = holder.getView(R.id.tvName)
        stv.setCenterString(item)

        val position = holder.layoutPosition
        when {
            position % columns == 0 -> {
                // 第一列，左边圆角，右边直角

                stv.shapeBuilder.apply {
                    setShapeCornersBottomLeftRadius(30f)
                    setShapeCornersTopLeftRadius(30f)
                    setShapeCornersBottomRightRadius(0f)
                    setShapeCornersTopRightRadius(0f)
                    into(stv)
                }
            }

            position % columns == columns - 1 -> {
                // 最后一列，左边直角，右边圆角
                stv.shapeBuilder.apply {
                    setShapeCornersTopRightRadius(30f)
                    setShapeCornersBottomRightRadius(30f)
                    setShapeCornersTopLeftRadius(0f)
                    setShapeCornersBottomLeftRadius(0f)
                    into(stv)
                }
            }
            else -> {
                // 中间列，全部直角
                stv.shapeBuilder.apply {
                    setShapeCornersBottomLeftRadius(0f)
                    setShapeCornersTopLeftRadius(0f)
                    setShapeCornersBottomRightRadius(0f)
                    setShapeCornersTopRightRadius(0f)
                    into(stv)
                }
            }
        }
    }
}