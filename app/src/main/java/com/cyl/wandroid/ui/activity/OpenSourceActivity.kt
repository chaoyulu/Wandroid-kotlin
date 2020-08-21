package com.cyl.wandroid.ui.activity

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseActivity
import com.cyl.wandroid.tools.start
import com.cyl.wandroid.ui.adapter.OpenSourceAdapter
import com.cyl.wandroid.ui.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.activity_open_source.*

class OpenSourceActivity : BaseActivity(), OnItemClickListener {

    val sources = listOf(
        Triple("Retrofit", "square", "https://github.com/square/retrofit"),
        Triple("OkHttp", "square", "https://github.com/square/okhttp"),
        Triple("Toasty", "GrenderG", "https://github.com/GrenderG/Toasty"),
        Triple(
            "BaseRecyclerViewAdapterHelper",
            "CymChad",
            "https://github.com/CymChad/BaseRecyclerViewAdapterHelper"
        ),
        Triple("ImmersionBar", "gyf-dev", "https://github.com/gyf-dev/ImmersionBar"),
        Triple("Banner", "youth5201314", "https://github.com/youth5201314/banner"),
        Triple("Glide", "bumptech", "https://github.com/bumptech/glide"),
        Triple("SuperTextView", "lygttpod", "https://github.com/lygttpod/SuperTextView"),
        Triple("LiveEventBus", "JeremyLiao", "https://github.com/JeremyLiao/LiveEventBus"),
        Triple("AgentWeb", "Justson", "https://github.com/Justson/AgentWeb"),
        Triple("FlowLayout", "hongyangAndroid", "https://github.com/hongyangAndroid/FlowLayout"),
        Triple(
            "PersistentCookieJar",
            "franmontiel",
            "https://github.com/franmontiel/PersistentCookieJar"
        ),
        Triple(
            "EasySwipeMenuLayout",
            "anzaizai",
            "https://github.com/anzaizai/EasySwipeMenuLayout"
        ),
        Triple("Android-PickerView", "Bigkoo", "https://github.com/Bigkoo/Android-PickerView")
    )

    private lateinit var adapter: OpenSourceAdapter

    override fun initData() {
    }

    override fun initView() {
        setCenterText(R.string.thanks_open)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        adapter = OpenSourceAdapter()
        adapter.setList(sources)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(this)
        recyclerView.addItemDecoration(SpaceItemDecoration(20))
    }

    override fun getLayoutRes() = R.layout.activity_open_source
    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        start(this, AgentWebActivity::class.java, Bundle().apply {
            putString(AgentWebActivity.URL, sources[position].third)
            putBoolean(AgentWebActivity.SHOW_COLLECT_ITEM, false)
        })
    }
}