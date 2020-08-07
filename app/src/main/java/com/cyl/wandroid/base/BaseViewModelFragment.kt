package com.cyl.wandroid.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider

abstract class BaseViewModelFragment<VM : BaseViewModel> : BaseFragment() {
    protected open lateinit var mViewModel: VM
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initView()
        observe()
        if (savedInstanceState == null) initData()
    }

    open fun initData() {}
    protected abstract fun initView()
    protected abstract fun observe() // LiveData发生变化通知界面改变

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this)[getViewModelClass()]
    }

    protected abstract fun getViewModelClass(): Class<VM>
}