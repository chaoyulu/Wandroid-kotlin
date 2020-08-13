package com.cyl.wandroid.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.cyl.wandroid.R

class OvalShadowView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {
    private var paint: Paint

    init {
        val ta: TypedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.OvalShadowView, 0, 0)
        val color = ta.getColor(
            R.styleable.OvalShadowView_shadow_color,
            ContextCompat.getColor(context, R.color.grey_771C1C1B)
        )

        paint = Paint()
        paint.isDither = true
        paint.isAntiAlias = true
        paint.color = color
        paint.style = Paint.Style.FILL
        ta.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawOval(0F, 0F, width.toFloat(), height.toFloat(), paint)
    }
}