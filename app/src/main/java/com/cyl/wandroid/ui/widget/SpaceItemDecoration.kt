package com.cyl.wandroid.ui.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
    private val spaceLeft: Int = 5,
    private val spaceTop: Int = spaceLeft,
    private val spaceRight: Int = spaceLeft,
    private val spaceBottom: Int = spaceLeft,
    private val orientation: Int = VERTICAL
) : RecyclerView.ItemDecoration() {

    companion object {
        const val LINEAR_LAYOUT = 0
        const val GRID_LAYOUT = 1
        const val VERTICAL = 3
    }

    /**
     * 头布局个数
     */
    private var headItemCount = 0

    /**
     * 时候包含边距
     */
    private var includeEdge = false

    /**
     * 列数
     */
    private var spanCount = 0

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        when (orientation) {
            LINEAR_LAYOUT -> setLinearLayoutSpaceItemDecoration(outRect, view, parent)
            GRID_LAYOUT -> {
                val gridLayoutManager = parent.layoutManager as GridLayoutManager
                spanCount = gridLayoutManager.spanCount
                setNGridLayoutSpaceItemDecoration(outRect, view, parent)
            }
            VERTICAL -> setVerticalSpace(outRect, view, parent)
        }
    }

    private fun setVerticalSpace(
        outRect: Rect, view: View,
        parent: RecyclerView
    ) {
        outRect.bottom = spaceBottom;
        outRect.left = spaceLeft;
        outRect.right = spaceRight;
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = spaceTop;
        } else {
            outRect.top = 0;
        }
    }

    private fun setLinearLayoutSpaceItemDecoration(
        outRect: Rect,
        view: View,
        parent: RecyclerView
    ) {
        outRect.left = spaceLeft;
        outRect.right = spaceRight;
        outRect.bottom = spaceBottom;
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = spaceTop;
        } else {
            outRect.top = 0;
        }
    }

    private fun setNGridLayoutSpaceItemDecoration(
        outRect: Rect,
        view: View,
        parent: RecyclerView
    ) {
        val position = parent.getChildAdapterPosition(view) - headItemCount
        if (headItemCount != 0 && position == -headItemCount) {
            return
        }
        val column = position % spanCount
        if (includeEdge) {
            outRect.left = spaceLeft - column * spaceLeft / spanCount;
            outRect.right = (column + 1) * spaceRight / spanCount;
            if (position < spanCount) {
                outRect.top = spaceTop;
            }
            outRect.bottom = spaceBottom;
        } else {
            outRect.left = column * spaceLeft / spanCount;
            outRect.right = spaceLeft - (column + 1) * spaceLeft / spanCount;
            if (position >= spanCount) {
                outRect.top = spaceTop;
            }
        }
    }
}