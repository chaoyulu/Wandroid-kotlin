package com.cyl.wandroid.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import com.cyl.wandroid.R
import com.cyl.wandroid.tools.getScreenWidth

class LoadingDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)

        setCanceledOnTouchOutside(false)
        setCancelable(false)
        setDialogDimensions()

//        progressBar.indeterminateDrawable.colorFilter =
//            BlendModeColorFilter(R.color.black, BlendMode.MULTIPLY)

        setDialogRadius()
    }

    private fun setDialogRadius() {
        val gradientDrawable = GradientDrawable()
        gradientDrawable.cornerRadius = 20f
        gradientDrawable.setColor(Color.WHITE)
        window?.decorView?.background = gradientDrawable
    }

    private fun setDialogDimensions() {
        window?.attributes?.apply {
            width = getScreenWidth(context) / 3
            height = getScreenWidth(context) / 3
        }
    }
}