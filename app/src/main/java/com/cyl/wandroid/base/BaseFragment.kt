package com.cyl.wandroid.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.toolbar_fragment.*

abstract class BaseFragment : Fragment() {
    protected lateinit var mContext: Context
    private var isLoaded = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initToolbar()
    }

    protected abstract fun initView()
    abstract fun getLayoutRes(): Int
    protected abstract fun lazyInitData()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded) {
            lazyInitData()
            isLoaded = true
        }
    }

    private fun initToolbar() {
        ivRight?.setOnClickListener {
            onRightIconClick()
        }
    }

    open fun onRightIconClick() {
        // Toolbar右上角按钮
    }

    fun setRightIcon(iconRes: Int) {
        ivRight?.let {
            it.isVisible = true
            it.setImageResource(iconRes)
        }
    }

    fun setCenterText(text: String) {
        tvCenter?.text = text
    }

    fun setCenterText(text: Int) {
        tvCenter?.setText(text)
    }
}