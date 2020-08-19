package com.cyl.wandroid.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.cyl.wandroid.R
import com.cyl.wandroid.ext.setCircularCorner
import com.cyl.wandroid.tools.getScreenWidth
import kotlinx.android.synthetic.main.dialog_loading.tvDesc
import kotlinx.android.synthetic.main.dialog_operate_tip.*

class OperateTipDialog(
    context: Context,
    val title: Int = R.string.warm_remind,
    private val desc: Int,
    private val cancelText: Int = R.string.cancel,
    private val confirmText: Int = R.string.confirm,
    private val isCancelable: Boolean = true,
    private val listener: OnTipDialogClickListener?
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_operate_tip)

        tvTitle.text = context.getString(title)
        tvDesc.text = context.getString(desc)
        tvCancel.text = context.getString(cancelText)
        tvConfirm.text = context.getString(confirmText)

        setCanceledOnTouchOutside(isCancelable)
        setCancelable(isCancelable)
        setDialogDimensions()

        setDialogRadius()

        tvCancel.setOnClickListener {
            dismiss()
            listener?.onCancel()
        }

        tvConfirm.setOnClickListener {
            dismiss()
            listener?.onConfirm()
        }
    }

    private fun setDialogRadius() {
        window?.decorView?.apply {
            setCircularCorner()
            setPadding(0, 0, 0, 0)
        }
    }

    private fun setDialogDimensions() {
        window?.attributes?.apply {
            width = 3 * getScreenWidth(context) / 4
        }
    }

    interface OnTipDialogClickListener {
        fun onCancel()
        fun onConfirm()
    }
}