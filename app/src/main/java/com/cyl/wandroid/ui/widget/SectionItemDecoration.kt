package com.cyl.wandroid.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cyl.wandroid.R
import com.cyl.wandroid.http.bean.NavigationBean

/**
 * 吸顶
 */
class SectionItemDecoration(val context: Context, var data: List<NavigationBean> = emptyList()) :
    RecyclerView.ItemDecoration() {
    private val mBgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mTextPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mBounds: Rect = Rect()
    private var mSectionHeight = 150

    private val dividerPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        val mBgColor = ContextCompat.getColor(context, R.color.white)
        val mTextColor = ContextCompat.getColor(context, R.color.black)
        mBgPaint.color = mBgColor

        mTextPaint.textSize = 50f
        mTextPaint.color = mTextColor

        dividerPaint.color = ContextCompat.getColor(context, R.color.yellow_77F8D073)
    }

    fun setDataList(data: List<NavigationBean>) {
        this.data = data
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val position = params.viewAdapterPosition
            if (data.isNotEmpty() && position <= data.size - 1 && position > -1) {
                if (position != 0) {
                    if (data[position].name != data[position - 1].name) {
                        drawSection(c, left, right, child, params, position);
                    }
                }
            }

            drawDivider(
                c,
                left.toFloat(), (child.top - params.topMargin).toFloat(),
                (child.top - params.topMargin).toFloat()
            )
        }
    }

    private fun drawDivider(c: Canvas, left: Float, top: Float, bottom: Float) {
        c.drawRoundRect(left + 40, top, 150f, bottom - 20, 10f, 10f, dividerPaint)
    }

    private fun drawSection(
        c: Canvas, left: Int, right: Int, child: View,
        params: RecyclerView.LayoutParams, position: Int
    ) {
        c.drawRect(
            left.toFloat(), (child.top - params.topMargin - mSectionHeight).toFloat(),
            right.toFloat(), (child.top - params.topMargin).toFloat(), mBgPaint
        )

        mTextPaint.getTextBounds(
            data[position].name, 0, data[position].name.length, mBounds
        )
        c.drawText(
            data[position].name,
            child.paddingLeft.toFloat() + 40,
            (child.top - params.topMargin - (mSectionHeight / 2 - mBounds.height() / 2)).toFloat(),
            mTextPaint
        )
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val pos = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        if (pos < 0) return
        if (data.isEmpty()) return
        val section = data[pos].name
        val child = parent.findViewHolderForAdapterPosition(pos)?.itemView
        var flag = false
        if (pos + 1 < data.size) {
            if (section != data[pos + 1].name) {
                child?.let {
                    if (child.height + child.top < mSectionHeight) {
                        c.save()
                        flag = true
                        c.translate(0f, (child.height + child.top - mSectionHeight).toFloat())
                    }
                }
            }
        }

        c.drawRect(
            parent.paddingLeft.toFloat(),
            parent.paddingTop.toFloat(),
            (parent.right - parent.paddingRight).toFloat(),
            (parent.paddingTop + mSectionHeight).toFloat(), mBgPaint
        )

        drawDivider(
            c,
            parent.paddingLeft.toFloat(),
            (parent.paddingTop + mSectionHeight).toFloat(),
            (parent.paddingTop + mSectionHeight).toFloat()
        )

        mTextPaint.getTextBounds(section, 0, section.length, mBounds)
        child?.let {
            c.drawText(
                section,
                child.paddingLeft.toFloat() + 40,
                (parent.paddingTop + mSectionHeight - (mSectionHeight / 2 - mBounds.height() / 2)).toFloat(),
                mTextPaint
            )

            if (flag) c.restore()
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position =
            (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        if (data.isNotEmpty() && position <= data.size - 1 && position > -1) {
            if (position == 0) {
                outRect[0, mSectionHeight, 0] = 0
            } else {
                if (data[position].name != data[position - 1].name
                ) {
                    outRect[0, mSectionHeight, 0] = 0
                }
            }
        }
    }
}