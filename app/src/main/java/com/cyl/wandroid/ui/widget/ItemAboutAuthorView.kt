package com.cyl.wandroid.ui.widget

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.cyl.wandroid.R
import com.cyl.wandroid.ext.setCircularCorner
import kotlinx.android.synthetic.main.item_about_author.view.*

class ItemAboutAuthorView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {
    init {
        LayoutInflater.from(context).inflate(R.layout.item_about_author, this)

        val ta: TypedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.ItemAboutAuthorView, 0, 0)

        val leadingIcon =
            ta.getResourceId(R.styleable.ItemAboutAuthorView_leadingIcon, R.mipmap.ic_launcher)
        val title = ta.getString(R.styleable.ItemAboutAuthorView_av_title)
        val name = ta.getString(R.styleable.ItemAboutAuthorView_av_name)
        val radius = ta.getDimension(R.styleable.ItemAboutAuthorView_av_radius, 30f)
        val color = ta.getColor(
            R.styleable.ItemAboutAuthorView_av_back_color,
            ContextCompat.getColor(context, R.color.yellow_77F8D073)
        )

        tvTitle.text = title
        tvName.text = name
        ivLeadingIcon.setImageResource(leadingIcon)
        setCircularCorner(color, radius)

        ta.recycle()
    }
}