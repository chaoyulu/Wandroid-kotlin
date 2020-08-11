package com.cyl.wandroid.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.cyl.wandroid.R
import com.cyl.wandroid.tools.imgTint
import kotlinx.android.synthetic.main.layout_home_item_view.view.*

class HomeItemView(context: Context, attributeSet: AttributeSet) :
    LinearLayout(context, attributeSet) {

    init {
        val ta: TypedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.HomeItemView, 0, 0)
        val text = ta.getString(R.styleable.HomeItemView_title)
        val textColor = ta.getColor(
            R.styleable.HomeItemView_text_color,
            ContextCompat.getColor(context, R.color.black)
        )
        val textSize = ta.getDimension(R.styleable.HomeItemView_text_size, 10f)
        val icon = ta.getResourceId(R.styleable.HomeItemView_icon, R.mipmap.ic_launcher)
        val tint = ta.getColor(
            R.styleable.HomeItemView_tint,
            ContextCompat.getColor(context, R.color.yellow_F8D073)
        )
        ta.recycle()
        LayoutInflater.from(context).inflate(R.layout.layout_home_item_view, this)
        // tint着色
        imgTint(ivIcon, tint)
        ivIcon.setImageResource(icon)

        tvText.text = text
        tvText.setTextColor(textColor)
        tvText.textSize = textSize
    }
}