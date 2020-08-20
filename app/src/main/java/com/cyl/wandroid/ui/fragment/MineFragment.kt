package com.cyl.wandroid.ui.fragment

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseViewModelFragment
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.MARK_COLLECT_LOGOUT_SUCCESS
import com.cyl.wandroid.common.bus.REFRESH_LOGIN_SUCCESS
import com.cyl.wandroid.http.bean.UserBean
import com.cyl.wandroid.sp.UserSpHelper
import com.cyl.wandroid.tools.start
import com.cyl.wandroid.ui.activity.*
import com.cyl.wandroid.viewmodel.MineViewModel
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.layout_my_info.view.*

class MineFragment : BaseViewModelFragment<MineViewModel>() {

    override fun getLayoutRes(): Int = R.layout.fragment_mine

    override fun lazyInitData() {
        myInfoCard.show()
        myInfoCard.civHeadIcon.setOnClickListener {
            if (!UserSpHelper.newHelper().isLogin()) {
                start(mContext, LoginActivity::class.java)
            }
        }

        busObserve()
        initClick()
    }

    override fun initView() {
        setCenterText(R.string.home_mine)
        val userBean = mViewModel.getUserInfo()
        if (userBean == null) {
            infoWhenLogout()
        } else {
            infoWhenLogin(userBean)
        }
    }

    private fun busObserve() {
        Bus.observe<UserBean>(REFRESH_LOGIN_SUCCESS, viewLifecycleOwner, observer = {
            UserSpHelper.newHelper().saveUserInfo(it)
            infoWhenLogin(userBean = it)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun infoWhenLogin(userBean: UserBean) {
        myInfoCard.tvNickname.text = userBean.nickname
        myInfoCard.tvId.text = "ID:${userBean.id}"
        myInfoCard.tvTranslucentId.text = userBean.id.toString()
        myInfoCard.civHeadIcon.text = userBean.nickname
        stvLogout.isVisible = true
    }

    private fun infoWhenLogout() {
        myInfoCard.tvNickname.setText(R.string.login_register)
        myInfoCard.tvId.setText(R.string.click_left_login)
        myInfoCard.tvTranslucentId.setText(R.string.ID)
        myInfoCard.civHeadIcon.setText(R.string.wan)
        stvLogout.isVisible = false
    }

    private fun initClick() {
        stvPoints.setOnClickListener {
            start(mContext, MyPointsActivity::class.java, needLogin = true)
        }
        stvPointsRank.setOnClickListener {
            start(mContext, PointsRankActivity::class.java, needLogin = true)
        }
        stvMyCollections.setOnClickListener {
            start(mContext, MyCollectionsActivity::class.java, needLogin = true)
        }
        stvMyShare.setOnClickListener {
            start(mContext, MySharedActivity::class.java, needLogin = true)
        }
        stvAddShare.setOnClickListener {
            start(mContext, AddShareActivity::class.java, needLogin = true)
        }
        stvMyTodo.setOnClickListener {
            start(mContext, MyTodoActivity::class.java, needLogin = true)
        }
        stvLookHistory.setOnClickListener {
        }
        stvAboutAuthor.setOnClickListener {
            start(mContext, AboutAuthorActivity::class.java)
        }
        stvLogout.setOnClickListener {
            mViewModel.logout()
        }
//        stvSystemSetting.setOnClickListener {
//            start(mContext, MyCollectionsActivity::class.java)
//        }
    }

    override fun observe() {
        mViewModel.logoutLiveData.observe(viewLifecycleOwner, Observer {
            // 退出登录后的操作
            infoWhenLogout()
            Bus.post(MARK_COLLECT_LOGOUT_SUCCESS, false)
        })
    }

    override fun getViewModelClass() = MineViewModel::class.java
}
