package com.cyl.wandroid.ui.widget

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextSwitcher
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.cyl.wandroid.R
import com.cyl.wandroid.http.bean.TodoBean
import java.util.*

class AdvertiseView @JvmOverloads constructor(
    private val mContext: Context,
    attrs: AttributeSet? = null
) : TextSwitcher(mContext, attrs) {
    private var mInterval = 3000 //文字停留在中间的时长
    private var mSizeCount = 0 //内容数量大小 = 0
    private val mHandler = Handler()
    private var mAnimationIn: Int = R.anim.anim_in_default //进入的动画
    private var mAnimationOut: Int = R.anim.anim_out_default //出去的动画

    private var mChangeListener: OnAdChangeListener? = null

    private var mCurrentIndex = 0 //当前的下标
    private var mDefaultTextView: TextView? = null //默认的文字

    private val mTexts: MutableList<TodoBean> = ArrayList()
    private var isScroll = false

    /**
     * 设置进入动画
     *
     * @param animationIn
     * @return
     */
    fun setAnimationIn(animationIn: Int): AdvertiseView {
        mAnimationIn = animationIn
        return this
    }

    /**
     * 设置间隔时间
     *
     * @param interval
     */
    fun setInterval(interval: Int): AdvertiseView {
        mInterval = interval
        return this
    }

    /**
     * 设置退出动画
     *
     * @return
     */
    fun setAnimationOut(animationOut: Int): AdvertiseView {
        mAnimationOut = animationOut
        return this
    }

    fun setOnAdChangeListener(listener: OnAdChangeListener) {
        mChangeListener = listener
    }

    fun init(texts: List<TodoBean>) {
        if (texts.isEmpty()) {
            return
        }
        mSizeCount = texts.size
        mCurrentIndex = 0
        mTexts.clear()
        mTexts.addAll(texts)

        //设置进入动画
        if (mAnimationIn != -1) {
            inAnimation = AnimationUtils.loadAnimation(
                mContext,
                mAnimationIn
            )
        }
        //设置出去动画
        if (mAnimationOut != -1) {
            outAnimation = AnimationUtils.loadAnimation(
                mContext,
                mAnimationOut
            )
        }
        //设置Factory
        if (mDefaultTextView == null) {
            setFactory {
                mDefaultTextView = TextView(mContext)
                mDefaultTextView!!.apply {
                    textSize = 14f
                    setTextColor(Color.BLACK)
                    gravity = Gravity.CENTER_VERTICAL
                    maxLines = 1
                }
                mDefaultTextView
            }
        }
        //开始滚动
        //设置文字
//        setText(mTexts[mCurrentIndex].title)
        setTodoText()
        isScroll = true
        mHandler.postDelayed(mRunnable, mInterval.toLong())
        setOnClickListener { v: View? ->
            if (mOnAdClickListener != null) mOnAdClickListener!!.onAdClick(
                mCurrentIndex
            )
        }
    }

    fun start() {
        isScroll = true
    }

    fun pause() {
        isScroll = false
    }

    private val mRunnable: Runnable = object : Runnable {
        override fun run() {
            if (isScroll) {
                //递增
                mCurrentIndex++
                if (mCurrentIndex >= mSizeCount) {
                    mCurrentIndex = 0
                }
                //设置i文字
                setTodoText()
//                            mChangeListener ?. diyTextView (currentView as TextView, mCurrentIndex
            }
            //进行下一次
            mHandler.removeCallbacks(this)
            mHandler.postDelayed(this, mInterval.toLong())
        }
    }

    private fun setTodoText() {
        setText(
            HtmlCompat.fromHtml(
                "<font color='red'>[今日有待办]</font>  ${mTexts[mCurrentIndex].title}",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
        )
    }

    private var mOnAdClickListener: OnAdClickListener? = null
    fun setOnAdClickListener(onAdClickListener: OnAdClickListener?) {
        mOnAdClickListener = onAdClickListener
    }

    interface OnAdChangeListener {
        fun diyTextView(textView: TextView?, index: Int)
    }

    interface OnAdClickListener {
        fun onAdClick(index: Int)
    }
}