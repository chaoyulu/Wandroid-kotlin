package com.cyl.wandroid.ui.activity

import androidx.core.view.isVisible
import com.cyl.wandroid.R
import com.cyl.wandroid.ui.fragment.HomePublicAccountFragment
import kotlinx.android.synthetic.main.toolbar_activity.*

/**
 * 包裹首页公众号Fragment的Activity容器
 */
class PublicAccountContainerActivity : FragmentContainerActivity<HomePublicAccountFragment>() {

    override fun createFragment(): HomePublicAccountFragment {
        return HomePublicAccountFragment()
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_public_account_container
    }

    override fun initView() {
        super.initView()
        ivRight.isVisible = false
    }

    override fun initData() {
        super.initData()
        setCenterText(R.string.home_public_account)
    }
}