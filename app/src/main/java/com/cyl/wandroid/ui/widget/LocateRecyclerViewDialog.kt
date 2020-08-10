package com.cyl.wandroid.ui.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.recyclerview.widget.GridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.cyl.wandroid.R
import com.cyl.wandroid.tools.getScreenWidth
import com.cyl.wandroid.ui.adapter.LocateRecyclerViewAdapter
import kotlinx.android.synthetic.main.dialog_locate_recycler_view.*

/**
 * 将多数据的RecyclerView的item标题显示出列表，快速定位
 */
class LocateRecyclerViewDialog(context: Context, private val titles: List<String>) :
    Dialog(context) {
    private lateinit var adapter: LocateRecyclerViewAdapter
    private val columns: Int = 3 // 设置列数
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_locate_recycler_view)

        setBackTransparent() // 设置Dialog背景透明
        setDialogGravity()
        setDialogDimensions()
        initRecyclerView()
    }

    private fun setDialogDimensions() {
        window?.attributes?.apply {
            width = getScreenWidth(context) /*/ 5 * 4*/
//            height = getScreenHeight(context) / 5 * 4
        }
    }

    fun initRecyclerView() {
        val manager = GridLayoutManager(context, columns)
        recyclerView.layoutManager = manager
        adapter = LocateRecyclerViewAdapter(columns)
        adapter.apply {
            animationEnable = true
            setAnimationWithDefault(BaseQuickAdapter.AnimationType.SlideInRight)
            setList(titles)
        }
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(SpaceItemDecoration(1))
    }

    private fun setBackTransparent() {
        val window = window
        val decorView = window?.decorView
        decorView?.background = ColorDrawable(Color.TRANSPARENT)
    }

    private fun setDialogGravity() {
        val attr = window?.attributes
        attr?.gravity = Gravity.CENTER
        window?.decorView?.setPadding(0, 0, 0, 0)
    }
}