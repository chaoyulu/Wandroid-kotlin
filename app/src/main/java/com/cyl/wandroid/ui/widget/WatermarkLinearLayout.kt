package com.cyl.wandroid.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.cyl.wandroid.R
import com.cyl.wandroid.tools.getTextWidth

class WatermarkLinearLayout(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {

    private val paint: Paint = Paint()
    private var text: String = ""

    init {
        val ta: TypedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.WatermarkLinearLayout, 0, 0)
        text = ta.getString(R.styleable.WatermarkLinearLayout_wll_text)!!
        val textColor = ta.getColor(
            R.styleable.WatermarkLinearLayout_wll_text_color,
            ContextCompat.getColor(context, R.color.translucent_white)
        )
        val textSize = ta.getDimension(R.styleable.WatermarkLinearLayout_wll_text_size, 250f)
        ta.recycle()

        paint.isDither = true
        paint.isAntiAlias = true
        paint.textSize = textSize
        paint.color = textColor
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        val x: Float = (width - getTextWidth(paint, text))
        val y = height.toFloat()
        canvas?.drawText(text, 0, text.length, x, y, paint)
    }
}