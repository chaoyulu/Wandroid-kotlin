package com.cyl.wandroid.ui.dialog

import android.content.Context
import android.os.Bundle
import androidx.core.view.isVisible
import com.cyl.wandroid.R
import com.cyl.wandroid.listener.OnAgentWebBottomDialogClickListener
import com.cyl.wandroid.sp.UserSpHelper
import com.cyl.wandroid.tools.setCollectionImgState
import kotlinx.android.synthetic.main.bottom_sheet_agent_web.*

class AgentWebBottomSheetDialog(
    context: Context,
    layoutRes: Int = R.layout.bottom_sheet_agent_web,
    private val isCollect: Boolean = false,
    private val showCollectItem: Boolean = true, // 从我的收藏中进入时不显示收藏按钮
    val listener: OnAgentWebBottomDialogClickListener
) : BaseBottomSheetDialog(context, layoutRes) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initClick()
    }

    private fun initView() {
        stvCollection.isVisible = UserSpHelper.newHelper().isLogin() && showCollectItem
        setCollectionImgState(context, stvCollection.leftIconIV, isCollect)
        stvCollection.setLeftString(
            if (isCollect) context.getString(R.string.cancel_collection) else context.getString(R.string.add_collection)
        )
    }

    private fun initClick() {
        stvCollection.setOnClickListener {
            listener.onCollectionClick()
            dismiss()
        }
        stvRefresh.setOnClickListener {
            listener.refreshWebPage()
            dismiss()
        }
        stvCopyLink.setOnClickListener {
            listener.copyLink()
            dismiss()
        }
        stvBrowserOpen.setOnClickListener {
            listener.openInBrowser()
            dismiss()
        }
        stvShare.setOnClickListener {
            listener.onShare()
            dismiss()
        }
    }
}