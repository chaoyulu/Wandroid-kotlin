package com.cyl.wandroid.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import com.cyl.wandroid.R
import com.cyl.wandroid.ext.setCircularCorner
import com.cyl.wandroid.listener.OnTagClickListener
import com.cyl.wandroid.tools.getScreenWidth
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import kotlinx.android.synthetic.main.dialog_locate_tag_view.*

/**
 * 将多数据的RecyclerView的item标题显示出列表，快速定位
 */
class LocateTagViewDialog(
    context: Context,
    private val titles: List<String>,
    private val listener: OnTagClickListener
) :
    Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_locate_tag_view)

        setBackTransparent() // 设置Dialog背景透明
        setDialogGravity()
        setDialogDimensions()
        initTag()
        setCanceledOnTouchOutside(false)
    }

    private fun setDialogDimensions() {
        window?.attributes?.apply {
            width = getScreenWidth(context)
        }
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
        attr?.dimAmount = 0.8f
    }

    private fun initTag() {
        val tagAdapter = object : TagAdapter<String>(titles) {
            override fun getView(
                parent: FlowLayout?,
                position: Int,
                t: String?
            ): View {
                val tv = LayoutInflater.from(context)
                    .inflate(R.layout.tag_view, tagFlowLayout, false) as TextView
                tv.text = t
                val bgColor = Color.WHITE
                val tagTextColor = Color.BLACK
                tv.setTextColor(tagTextColor)
                tv.setCircularCorner(bgColor)
                return tv
            }
        }
        tagFlowLayout.adapter = tagAdapter
        tagFlowLayout.setOnTagClickListener { _, position, _ ->
            listener.onTagClick(position, position)
            dismiss()
            return@setOnTagClickListener true
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (isOutOfDialog(event) && event.action == MotionEvent.ACTION_UP) {
            startDismissDialogAnimation()
        }
        return super.onTouchEvent(event)
    }

    private fun startDismissDialogAnimation() {
        val animation: Animation = AnimationUtils.loadAnimation(
            context, R.anim.anim_item_out
        )
        animation.duration = 500
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                dismiss()
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        tagFlowLayout.startAnimation(animation)
    }

    private fun isOutOfDialog(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        val slop = ViewConfiguration.get(context).scaledWindowTouchSlop
        val window: Window = window ?: return true
        val decorView = window.decorView
        return (x < -slop) || (y < -slop) || (x > (decorView.width + slop))
                || (y > (decorView.height + slop));
    }
}