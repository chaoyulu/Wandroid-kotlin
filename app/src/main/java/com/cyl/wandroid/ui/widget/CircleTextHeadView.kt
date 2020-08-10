package com.cyl.wandroid.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity

/**
 * 圆形边框，只显示一个字符的TextView
 */
class CircleTextHeadView(context: Context, attributeSet: AttributeSet) :
    androidx.appcompat.widget.AppCompatTextView(context, attributeSet) {

    private var borderPaint: Paint = Paint()

    init {
        borderPaint.isDither = true
        borderPaint.isAntiAlias = true
        borderPaint.strokeWidth = 3.0f
        borderPaint.color = Color.WHITE
        borderPaint.style = Paint.Style.STROKE
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
        super.onDraw(canvas)
        canvas?.drawCircle(
            (width / 2).toFloat(), (height / 2).toFloat(),
            (width / 2).toFloat(), borderPaint
        )
    }
}