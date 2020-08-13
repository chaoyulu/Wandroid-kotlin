package com.cyl.wandroid.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import com.cyl.wandroid.R

/**
 * 圆形边框，只显示一个字符的TextView
 */
class CircleTextHeadView(context: Context, attributeSet: AttributeSet) :
    androidx.appcompat.widget.AppCompatTextView(context, attributeSet) {

    private var borderPaint: Paint = Paint()

    init {
        val ta: TypedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.CircleTextHeadView, 0, 0)
        val color = ta.getColor(R.styleable.CircleTextHeadView_color, Color.WHITE)
        val strokeWidth = ta.getDimension(R.styleable.CircleTextHeadView_stroke_width, 3f)
        val style = ta.getInt(R.styleable.CircleTextHeadView_style, 0)

        ta.recycle()
        borderPaint.isDither = true
        borderPaint.isAntiAlias = true
        borderPaint.strokeWidth = strokeWidth
        borderPaint.color = color
        borderPaint.style = if (style == 0) Paint.Style.STROKE else Paint.Style.FILL
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        gravity = Gravity.CENTER
        val result = if (text == null || text.isEmpty()) {
            "玩"
        } else {
            text.subSequence(0, 1)
        }
        super.setText(result, type)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawCircle(
            (width / 2).toFloat(), (height / 2).toFloat(),
            (width / 2).toFloat(), borderPaint
        )
        super.onDraw(canvas)
    }
}