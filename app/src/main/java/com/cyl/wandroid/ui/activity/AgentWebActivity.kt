package com.cyl.wandroid.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.KeyEvent
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.lifecycle.Observer
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseViewModelActivity
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.CANCEL_COLLECT_SUCCESS
import com.cyl.wandroid.common.bus.COLLECT_SUCCESS
import com.cyl.wandroid.listener.OnAgentWebBottomDialogClickListener
import com.cyl.wandroid.tools.copyText
import com.cyl.wandroid.tools.showNormal
import com.cyl.wandroid.tools.whiteList
import com.cyl.wandroid.ui.dialog.AgentWebBottomSheetDialog
import com.cyl.wandroid.ui.widget.GradientWebIndicator
import com.cyl.wandroid.viewmodel.CollectViewModel
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.just.agentweb.WebChromeClient
import com.just.agentweb.WebViewClient
import kotlinx.android.synthetic.main.activity_agent_web.*
import kotlinx.android.synthetic.main.toolbar_activity.*

class AgentWebActivity : BaseViewModelActivity<CollectViewModel>(),
    OnAgentWebBottomDialogClickListener {
    private lateinit var url: String
    private var agentWeb: AgentWeb? = null
    private var isCollect = false
    private var id = 0
    private var showCollectItem = true

    companion object {
        const val URL: String = "url"
        const val IS_COLLECT = "isCollect"
        const val ID = "id"
        const val SHOW_COLLECT_ITEM = "show_collect_item"
    }

    override fun initData() {
        url = intent?.extras?.getString(URL) ?: return
        isCollect = intent?.extras?.getBoolean(IS_COLLECT) ?: return
        id = intent?.extras?.getInt(ID) ?: return
        showCollectItem = intent?.extras?.getBoolean(SHOW_COLLECT_ITEM, true) ?: return

        initAgentWeb()
        showCloseIcon()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initAgentWeb() {
        agentWeb =
            AgentWeb.with(this).setAgentWebParent(frameLayout, ViewGroup.LayoutParams(-1, -1))
                .setCustomIndicator(GradientWebIndicator(this)).interceptUnkownUrl()
//                .setMainFrameErrorView(R.layout.web_error_load, R.id.ll_reload)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
                .setWebChromeClient(object : WebChromeClient() {
                    override fun onReceivedTitle(view: WebView?, title: String?) {
                        tvCenter.text = title
                        super.onReceivedTitle(view, title)
                    }

                    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
                        println(consoleMessage)
                        return super.onConsoleMessage(consoleMessage)
                    }
                }).setWebViewClient(object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(
                        view: WebView?,
                        request: WebResourceRequest?
                    ): Boolean {
                        return !whiteList.contains(request?.url?.host)
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        view?.loadUrl(filterJS(url))
                    }
                }).createAgentWeb().ready().go(url)

        val settings = agentWeb?.agentWebSettings?.webSettings
        settings?.apply {
            javaScriptEnabled = true
            loadWithOverviewMode = true
        }
    }

    override fun initView() {
        setRightIcon(R.mipmap.icon_more)
    }

    override fun getLayoutRes() = R.layout.activity_agent_web

    private fun filterJS(url: String?): String {
        val sb = StringBuilder()
        sb.append("javascript:(function(){")
        when (Uri.parse(url).host) {
            "juejin.im" -> {
                sb.append("var headerList = document.getElementsByClassName('main-header-box');")
                sb.append("if(headerList&&headerList.length){headerList[0].parentNode.removeChild(headerList[0])}")
                sb.append("var openAppList = document.getElementsByClassName('open-in-app');")
                sb.append("if(openAppList&&openAppList.length){openAppList[0].parentNode.removeChild(openAppList[0])}")
                sb.append("var actionBox = document.getElementsByClassName('action-box');")
                sb.append("if(actionBox&&actionBox.length){actionBox[0].parentNode.removeChild(actionBox[0])}")
                sb.append("var actionBarList = document.getElementsByClassName('action-bar');")
                sb.append("if(actionBarList&&actionBarList.length){actionBarList[0].parentNode.removeChild(actionBarList[0])}")
                sb.append("var columnViewList = document.getElementsByClassName('column-view');")
                sb.append("if(columnViewList&&columnViewList.length){columnViewList[0].style.margin = '0px'}")
            }
            "www.jianshu.com" -> {
                sb.append("var jianshuHeader = document.getElementById('jianshu-header');")
                sb.append("if(jianshuHeader){jianshuHeader.parentNode.removeChild(jianshuHeader)}")
                sb.append("var headerShimList = document.getElementsByClassName('header-shim');")
                sb.append("if(headerShimList&&headerShimList.length){headerShimList[0].parentNode.removeChild(headerShimList[0])}")
                sb.append("var fubiaoList = document.getElementsByClassName('fubiao-dialog');")
                sb.append("if(fubiaoList&&fubiaoList.length){fubiaoList[0].parentNode.removeChild(fubiaoList[0])}")
                sb.append("var ads = document.getElementsByClassName('note-comment-above-ad-wrap');")
                sb.append("if(ads&&ads.length){ads[0].parentNode.removeChild(ads[0])}")

                sb.append("var lazyShimList = document.getElementsByClassName('v-lazy-shim');")
                sb.append("if(lazyShimList&&lazyShimList.length&&lazyShimList[0]){lazyShimList[0].parentNode.removeChild(lazyShimList[0])}")
                sb.append("if(lazyShimList&&lazyShimList.length&&lazyShimList[1]){lazyShimList[1].parentNode.removeChild(lazyShimList[1])}")
            }
            "blog.csdn.net" -> {
                sb.append("var csdnToolBar = document.getElementById('csdn-toolbar');")
                sb.append("if(csdnToolBar){csdnToolBar.parentNode.removeChild(csdnToolBar)}")
                sb.append("var csdnMain = document.getElementById('main');")
                sb.append("if(csdnMain){csdnMain.style.margin='0px'}")
                sb.append("var operate = document.getElementById('operate');")
                sb.append("if(operate){operate.parentNode.removeChild(operate)}")
            }
        }
        sb.append("})()")
        return sb.toString()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (agentWeb?.handleKeyEvent(keyCode, event) == true) {
            return true
        } else {
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onPause() {
        agentWeb?.webLifeCycle?.onPause()
        super.onPause()

    }

    override fun onResume() {
        agentWeb?.webLifeCycle?.onResume()
        super.onResume()
    }

    override fun onDestroy() {
        agentWeb?.webLifeCycle?.onDestroy()
        super.onDestroy()
    }

    override fun onBackClick(): Boolean {
        if (agentWeb == null) {
            return true
        }

        if (!agentWeb!!.back()) {
            finish()
        }
        return false
    }

    override fun onRightIconClick() {
        super.onRightIconClick()
        AgentWebBottomSheetDialog(
            this,
            listener = this,
            isCollect = isCollect,
            showCollectItem = showCollectItem
        ).show()
    }

    override fun onCollectionClick() {
        if (isCollect) {
            // 取消收藏
            mViewModel.cancelCollectFromArticleList(id)
        } else {
            // 加入收藏
            mViewModel.collect(id)
        }
    }

    override fun refreshWebPage() {
        agentWeb?.urlLoader?.reload()
    }

    override fun copyLink() {
        copyText(url, url)
        showNormal(R.string.link_copy_success)
    }

    override fun openInBrowser() {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    override fun onShare() {
    }

    override fun observe() {
        mViewModel.apply {
            collectLiveData.observe(this@AgentWebActivity, Observer {
                showNormal(if (it.second) R.string.already_add_collection else R.string.already_cancel_collection)
                isCollect = it.second
                Bus.post(if (it.second) COLLECT_SUCCESS else CANCEL_COLLECT_SUCCESS, it.first)
            })
        }
    }

    override fun getViewModelClass() = CollectViewModel::class.java
}
