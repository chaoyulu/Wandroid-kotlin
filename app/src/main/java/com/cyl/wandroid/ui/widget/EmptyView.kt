package com.cyl.wandroid.ui.widget

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.cyl.wandroid.R
import kotlinx.android.synthetic.main.layout_empty_view.view.*

class EmptyView(context: Context) : LinearLayout(context) {

    private var icon = R.mipmap.icon_empty
    private var textRes = R.string.empty_text

    fun setEmptyInfo(icon: Int = R.mipmap.icon_empty, textRes: Int = R.string.empty_text) {
        this.icon = icon
        this.textRes = textRes

        ivEmpty.setImageResource(icon)
        tvEmpty.text = context.getString(textRes)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this)

    }
}