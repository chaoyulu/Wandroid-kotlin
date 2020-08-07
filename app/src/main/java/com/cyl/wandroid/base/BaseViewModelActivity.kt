package com.cyl.wandroid.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

abstract class BaseViewModelActivity<VM : BaseViewModel> : BaseActivity() {
    protected open lateinit var mViewModel: VM
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        observe()
    }

    protected abstract fun observe() // LiveData发生变化通知界面改变

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this)[getViewModelClass()]
    }

    protected abstract fun getViewModelClass(): Class<VM>
}