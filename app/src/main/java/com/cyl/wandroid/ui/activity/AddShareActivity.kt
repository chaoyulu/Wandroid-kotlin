package com.cyl.wandroid.ui.activity

import androidx.lifecycle.Observer
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseViewModelActivity
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.REFRESH_ADD_SHARE_SUCCESS
import com.cyl.wandroid.http.api.RequestState
import com.cyl.wandroid.tools.showError
import com.cyl.wandroid.tools.showNormal
import com.cyl.wandroid.tools.start
import com.cyl.wandroid.ui.dialog.LoadingDialog
import com.cyl.wandroid.viewmodel.AddSharedViewModel
import kotlinx.android.synthetic.main.activity_add_share.*

class AddShareActivity : BaseViewModelActivity<AddSharedViewModel>() {
    private var loadingDialog: LoadingDialog? = null

    override fun getViewModelClass() = AddSharedViewModel::class.java

    override fun initData() {
    }

    override fun initView() {
        setCenterText(R.string.add_share)
        setRightIcon(R.mipmap.icon_submit)

        val userInfo = mViewModel.getUserInfo()
        userInfo?.let {
            // 分享文章会默认使用你的昵称，没有昵称会使用用户名
            tvShareUser.text = if (it.nickname.isEmpty()) it.username else it.nickname
        }
    }

    override fun getLayoutRes() = R.layout.activity_add_share

    override fun onRightIconClick() {
        super.onRightIconClick()
        submit()
    }

    private fun submit() {
        val title = etTitle.text.toString()
        val link = etLink.text.toString()
        if (title.isEmpty()) {
            showError(R.string.fill_share_title)
            return
        }
        if (link.isEmpty()) {
            showError(R.string.fill_share_link)
            return
        }

        if (loadingDialog == null) loadingDialog = LoadingDialog(this)
        mViewModel.submitShare(title, link)
    }

    override fun observe() {
        mViewModel.apply {
            sharedSuccess.observe(this@AddShareActivity, Observer {
                if (it) {
                    showNormal(R.string.share_success)
                    start(this@AddShareActivity, MySharedActivity::class.java, needLogin = true)
                    Bus.post(REFRESH_ADD_SHARE_SUCCESS, 0)
                    finish()
                }
            })

            requestState.observe(this@AddShareActivity, Observer {
                when (it.name) {
                    RequestState.START.name -> {
                        loadingDialog?.show()
                    }

                    RequestState.END.name -> {
                        loadingDialog?.dismiss()
                    }
                }
            })
        }
    }
}