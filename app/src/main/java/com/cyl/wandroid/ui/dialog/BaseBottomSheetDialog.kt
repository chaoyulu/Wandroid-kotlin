package com.cyl.wandroid.ui.dialog

import android.content.Context
import com.cyl.wandroid.R
import com.google.android.material.bottomsheet.BottomSheetDialog

open class BaseBottomSheetDialog(context: Context, layoutRes: Int) :
    BottomSheetDialog(context, R.style.BottomSheetDialog) {

    init {
        this.setContentView(layoutRes)
    }
}