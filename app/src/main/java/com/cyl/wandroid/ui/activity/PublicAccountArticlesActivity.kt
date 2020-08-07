package com.cyl.wandroid.ui.activity

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.cyl.wandroid.R
import com.cyl.wandroid.base.BaseActivity
import com.cyl.wandroid.common.bus.Bus
import com.cyl.wandroid.common.bus.SCROLL_HOME_PUBLIC_ACCOUNT_POSITION
import com.cyl.wandroid.http.bean.PublicAccountBean
import com.cyl.wandroid.ui.adapter.ViewPagerAdapter
import com.cyl.wandroid.ui.fragment.PublicAccountArticlesSubFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_public_account_articles.*
import kotlinx.android.synthetic.main.layout_tab.*

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
    }

    private fun loadFragments() {
        accounts.forEach {
            titles.add(it.name)
            fragments.add(PublicAccountArticlesSubFragment.newFragment(it.id))
            tabLayout.addTab(tabLayout.newTab().setText(it.name))
        }
    }

    private fun initTabLayout() {
        val adapter = ViewPagerAdapter(supportFragmentManager, fragments, titles)
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = fragments.size
        viewPager.setCurrentItem(position, true)
        tabLayout.getTabAt(position)?.select()
        setCenterText(titles[position])
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                setCenterText(tab?.text.toString())
                scrollTab(tab?.position)
            }
        })

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                scrollTab(position)
                setCenterText(titles[position])
            }
        })
    }

    private fun scrollTab(position: Int?) {
        Bus.post(SCROLL_HOME_PUBLIC_ACCOUNT_POSITION, position)
    }
}
