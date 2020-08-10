package com.cyl.wandroid.ui.activity

import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseActivity
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.SCROLL_HOME_PUBLIC_ACCOUNT_POSITION
import com.cyl.wandroid.ext.addOnPageChangedListener
import com.cyl.wandroid.ext.addOnTabChangeListener
import com.cyl.wandroid.http.bean.PublicAccountBean
import com.cyl.wandroid.ui.adapter.ViewPagerAdapter
import com.cyl.wandroid.ui.fragment.PublicAccountArticlesSubFragment
import kotlinx.android.synthetic.main.activity_public_account_articles.*
import kotlinx.android.synthetic.main.layout_tab.*
import kotlinx.android.synthetic.main.toolbar_activity.*

class PublicAccountArticlesActivity : BaseActivity() {
    private lateinit var accounts: ArrayList<PublicAccountBean>
    private var position = 0
    private var fragments: MutableList<Fragment> = mutableListOf()
    private val titles: MutableList<String> = mutableListOf()

    override fun initData() {
        intent?.extras?.apply {
            position = getInt("position")
            accounts =
                getParcelableArrayList<PublicAccountBean>("accounts") as ArrayList<PublicAccountBean>
        }

        loadFragments()
        initTabLayout()
    }

    override fun getLayoutRes() = R.layout.activity_public_account_articles

    override fun initView() {
        ivRight.isVisible = false
    }

    private fun loadFragments() {
        accounts.forEach {
            titles.add(it.name)
            fragments.add(PublicAccountArticlesSubFragment.newFragment(it.id))
            tabLayout.addTab(tabLayout.newTab().setText(it.name))
        }
    }

    private fun initTabLayout() {
        setCenterText(titles[position])
        val adapter = ViewPagerAdapter(supportFragmentManager, fragments, titles)
        viewPager.apply {
            this.adapter = adapter
            offscreenPageLimit = fragments.size
            setCurrentItem(position, true)

            addOnPageChangedListener {
                postScrollTab(it)
                setCenterText(titles[it])
                viewPager.setCurrentItem(it, true)
                tabLayout.getTabAt(it)?.select()
            }
        }

        tabLayout.apply {
            post { tabLayout.getTabAt(position)?.select() }
            addOnTabChangeListener(selected = {
                it?.let {
                    setCenterText(it.text.toString())
                    postScrollTab(it.position)
                    viewPager.setCurrentItem(it.position, true)
                    tabLayout.getTabAt(it.position)?.select()
                }
            }, reselected = {})
        }
    }

    private fun postScrollTab(position: Int?) {
        Bus.post(SCROLL_HOME_PUBLIC_ACCOUNT_POSITION, position)
    }
}
