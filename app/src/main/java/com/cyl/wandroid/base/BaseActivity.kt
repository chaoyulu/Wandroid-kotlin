package com.cyl.wandroid.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cyl.wandroid.R
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.toolbar_activity.*

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())
        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor(R.color.white)
            .statusBarDarkFont(true).init()
        initView()
        initToolbar()
        if (savedInstanceState == null) initData()
    }

    protected abstract fun initData()
    protected abstract fun initView()
    abstract fun getLayoutRes(): Int

    private fun initToolbar() {
        tvBack?.setOnClickListener {
            onBackClick()
            finish()
        }

        ivRight?.setOnClickListener {
            onRightIconClick()
        }
    }

    fun onBackClick() {
        // 返回额外的工作
    }

    fun onRightIconClick() {
        // Toolbar右上角按钮
    }

    fun setRightIcon(iconRes: Int) {
        ivRight?.setBackgroundResource(iconRes)
    }

    fun setBackText(text: String) {
        tvBack?.text = text
    }

    fun setCenterText(text: String) {
        tvCenter?.text = text
    }
}
