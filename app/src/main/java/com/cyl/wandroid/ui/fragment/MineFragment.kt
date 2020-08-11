package com.cyl.wandroid.ui.fragment

import android.annotation.SuppressLint
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseFragment
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.REFRESH_LOGIN_SUCCESS
import com.cyl.wandroid.http.bean.UserBean
import com.cyl.wandroid.sp.UserSpHelper
import com.cyl.wandroid.tools.IntentTools
import com.cyl.wandroid.tools.checkLoginThenAction
import com.cyl.wandroid.ui.activity.LoginActivity
import com.cyl.wandroid.ui.activity.MyPointsActivity
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.layout_my_info.view.*

class MineFragment : BaseFragment() {

    override fun getLayoutRes(): Int = R.layout.fragment_mine

    override fun lazyInitData() {
        myInfoCard.show()
        myInfoCard.civHeadIcon.setOnClickListener {
            if (!UserSpHelper.newHelper().isLogin()) {
                IntentTools.start(mContext, LoginActivity::class.java)
            }
        }

        busObserve()
        initClick()
    }

    override fun initView() {
        setCenterText(R.string.home_mine)
        val userBean = UserSpHelper.newHelper().getUserInfo()
        if (userBean == null) {
            infoWhenLogout()
        } else {
            infoWhenLogin(userBean)
        }
    }

    private fun busObserve() {
        Bus.observe<UserBean>(REFRESH_LOGIN_SUCCESS, viewLifecycleOwner, observer = {
            infoWhenLogin(userBean = it)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun infoWhenLogin(userBean: UserBean) {
        myInfoCard.tvNickname.text = userBean.nickname
        myInfoCard.tvId.text = "ID:${userBean.id}"
        myInfoCard.tvTranslucentId.text = userBean.id.toString()
        myInfoCard.civHeadIcon.text = userBean.nickname
    }

    private fun infoWhenLogout() {
        myInfoCard.tvNickname.setText(R.string.login_register)
        myInfoCard.tvId.setText(R.string.click_left_login)
        myInfoCard.tvTranslucentId.setText(R.string.ID)
        myInfoCard.civHeadIcon.setText(R.string.wan)
    }

    private fun initClick() {
        stvPoints.setOnClickListener {
            checkLoginThenAction(mContext, action = {
                IntentTools.start(mContext, MyPointsActivity::class.java)
            })
        }
        stvPointsRank.setOnClickListener { }
        stvMyCollections.setOnClickListener { }
        stvMyShare.setOnClickListener { }
        stvMyTodo.setOnClickListener { }
        stvLookHistory.setOnClickListener { }
        stvAboutAuthor.setOnClickListener { }
        stvSystemSetting.setOnClickListener { }
    }
}
