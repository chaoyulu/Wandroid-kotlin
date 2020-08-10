package com.cyl.wandroid.ui.fragment

import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseFragment
import com.cyl.wandroid.tools.IntentTools
import com.cyl.wandroid.ui.activity.LoginActivity
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.layout_my_info.view.*

class MineFragment : BaseFragment() {

    override fun getLayoutRes(): Int = R.layout.fragment_mine

    override fun lazyInitData() {
        myInfoCard.show()
        myInfoCard.civHeadIcon.setOnClickListener {
            IntentTools.start(mContext, LoginActivity::class.java, null)
        }
    }

    override fun initView() {
        setCenterText(R.string.home_mine)
    }
}
