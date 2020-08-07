package com.cyl.wandroid.ui.widget

import android.graphics.Canvas
import androidx.recyclerview.widget.RecyclerView

class LineItemDecoration(private val lineHeight: Int, private val lineColor: Int) :
    RecyclerView.ItemDecoration() {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)

    }
}