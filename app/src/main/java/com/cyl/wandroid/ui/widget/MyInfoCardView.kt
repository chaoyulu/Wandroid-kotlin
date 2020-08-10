package com.cyl.wandroid.ui.widget

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.cyl.wandroid.R

class MyInfoCardView(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    private var borderRadius: Float = 50f
    private var backColor = R.color.yellow_F8D073
    private lateinit var headText: String
    private lateinit var nickname: String
    private lateinit var myId: String
    private var nickTextColor = R.color.white
    private var idTextColor = R.color.white

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_my_info, this)
    }

    public fun setInfo(
        borderRadius: Float,
        backColor: Int,
        headText: String,
        nickname: String,
        nickTextColor: Int = R.color.white,
        myId: String,
        idTextColor: Int = R.color.white
    ) {
        this.borderRadius = borderRadius
        this.backColor = backColor
        this.headText = headText
        this.nickname = nickname
        this.nickTextColor = nickTextColor
        this.myId = myId
        this.idTextColor = idTextColor
    }

    fun show() {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setColor(ContextCompat.getColor(context, backColor))
        gradientDrawable.cornerRadius = borderRadius
        background = gradientDrawable
    }
}