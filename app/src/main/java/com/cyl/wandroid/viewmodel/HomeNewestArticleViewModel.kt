package com.cyl.wandroid.viewmodel

import androidx.lifecycle.MutableLiveData
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.http.bean.HomeBannerBean
import com.cyl.wandroid.repository.HomeTopArticleRepository

class HomeNewestArticleViewModel : CollectViewModel() {
    private val homeTopArticleRepository by lazy { HomeTopArticleRepository() }

    val articles: MutableLiveData<MutableList<ArticleBean>> = MutableLiveData()
    val banners = MutableLiveData<List<HomeBannerBean>>()

    private var page = 0

    // 获取置顶文章和第一页首页文章
    fun refreshHomeNewestArticle() {
        launch(block = {
            setRefreshStatus(true)
            val homeTopArticles = async {
                homeTopArticleRepository.getHomeTopArticles()
            }.await()

            homeTopArticles.forEach {
                it.stick = true
            }

            val homePageArticles = async {
                homeTopArticleRepository.getHomePageArticles(0)
            }.await()

            page = homePageArticles.curPage
            articles.value = mutableListOf<ArticleBean>().apply {
                addAll(homeTopArticles)
                addAll(homePageArticles.datas)
            }
            setRefreshStatus(false)
        })
    }

    // 加载更多首页文章
    fun loadMoreArticles() {
        launch(block = {
            setLoadMoreStart()
            val homePageArticles = homeTopArticleRepository.getHomePageArticles(page)
            page = homePageArticles.curPage
            // articles.value不为空则将articles.value的值赋值给data，否则重新初始化
            val data = articles.value ?: mutableListOf()
            data.addAll(homePageArticles.datas)
            articles.value = data
            setLoadMoreFinishStatus(homePageArticles.offset, homePageArticles.total)
        })
    }

    fun getHomeBanner() {
        launch(block = {
            banners.value = homeTopArticleRepository.getHomeBanner()
        })
    }
}