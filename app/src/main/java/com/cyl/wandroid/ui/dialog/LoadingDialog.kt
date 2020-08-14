package com.cyl.wandroid.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.cyl.wandroid.R
import com.cyl.wandroid.ext.setCircularCorner
import com.cyl.wandroid.tools.getScreenWidth
import kotlinx.android.synthetic.main.dialog_loading.*

class LoadingDialog(context: Context) : Dialog(context) {
    private var desc: Int = R.string.please_wait

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)

        tvDesc.text = context.getString(desc)

        setCanceledOnTouchOutside(false)
        setCancelable(false)
        setDialogDimensions()

        setDialogRadius()
    }

    private fun setDialogRadius() {
        window?.decorView?.apply {
            setCircularCorner()
            setPadding(0, 0, 0, 0)
        }
    }

    private fun setDialogDimensions() {
        window?.attributes?.apply {
            width = getScreenWidth(context) / 3
            height = getScreenWidth(context) / 3
        }
    }

    fun setDesc(desc: Int) {
        this.desc = desc
    }
}