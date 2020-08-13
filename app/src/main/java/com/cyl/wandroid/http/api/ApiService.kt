package com.cyl.wandroid.http.api

import com.cyl.wandroid.http.bean.*
import retrofit2.http.*

interface ApiService {
    companion object {
        const val BASE_URL = "https://www.wanandroid.com/"
    }

    // 首页热门博文(热门博文 = 置顶文章 + 首页文章) - 置顶文章
    @GET("article/top/json")
    suspend fun getHomeTopArticles(): ApiCommonResponse<List<ArticleBean>>

    // 首页热门博文(热门博文 = 置顶文章 + 首页文章) - 首页文章列表
    @GET("article/list/{page}/json")
    suspend fun getHomePageArticles(@Path("page") page: Int): ApiCommonResponse<CommonArticleData<ArticleBean>>

    // 首页Banner
    @GET("banner/json")
    suspend fun getHomeBanner(): ApiCommonResponse<List<HomeBannerBean>>

    // 获取最新项目（首页第二个TAB）
    @GET("article/listproject/{page}/json")
    suspend fun getHomeNewestProjects(@Path("page") page: Int): ApiCommonResponse<CommonArticleData<ArticleBean>>

    // 首页最新分享（广场）
    @GET("user_article/list/{page}/json")
    suspend fun getHomeNewestShare(@Path("page") page: Int): ApiCommonResponse<CommonArticleData<ArticleBean>>

    // 首页公众号列表
    @GET("wxarticle/chapters/json")
    suspend fun getPublicAccountList(): ApiCommonResponse<List<PublicAccountBean>>

    // 首页公众号文章列表
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getPublicAccountArticles(
        @Path("id") id: Int, @Path("page") page: Int
    ): ApiCommonResponse<CommonArticleData<ArticleBean>>

    @GET("project/tree/json")
    suspend fun getProjectCategory(): ApiCommonResponse<List<ProjectCategoryBean>>

    // 项目文章
    @GET("project/list/{page}/json")
    suspend fun getProjectArticles(
        @Path("page") page: Int, @Query("cid") cid: Int
    ): ApiCommonResponse<CommonArticleData<ArticleBean>>

    // 体系分类
    @GET("tree/json")
    suspend fun getSystemCategory(): ApiCommonResponse<List<SystemCategoryBean>>

    // 体系文章
    @GET("article/list/{page}/json")
    suspend fun getSystemArticles(
        @Path("page") page: Int, @Query("cid") cid: Int
    ): ApiCommonResponse<CommonArticleData<ArticleBean>>

    // 导航分类
    @GET("navi/json")
    suspend fun getNavigation(): ApiCommonResponse<List<NavigationBean>>

    // 登录
    @POST("user/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): ApiCommonResponse<UserBean>

    // 注册
    @POST("user/register")
    @FormUrlEncoded
    suspend fun register(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("repassword") repassword: String
    ): ApiCommonResponse<UserBean>

    // 获取个人积分数和排名
    @GET("lg/coin/userinfo/json")
    suspend fun getMyPointsInfo(): ApiCommonResponse<PointRankBean>

    // 获取个人积分列表
    @GET("lg/coin/list/{page}/json")
    suspend fun getMyPointsList(@Path("page") page: Int): ApiCommonResponse<CommonArticleData<PointsListBean>>

    // 获取积分排行榜
    @GET("coin/rank/{page}/json")
    suspend fun getAllPointsRank(@Path("page") page: Int): ApiCommonResponse<CommonArticleData<PointRankBean>>

    // 收藏文章列表
    @GET("lg/collect/list/{page}/json")
    suspend fun getMyCollections(@Path("page") page: Int): ApiCommonResponse<CommonArticleData<ArticleBean>>

    // 收藏站内文章
    @GET("lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int)

    // 我的分享列表
    @GET("user/lg/private_articles/{page}/json")
    suspend fun getMyShared(@Path("page") page: Int): ApiCommonResponse<ShareBean>

    // 别人的分享列表
    @GET("user/{id}/share_articles/{page}/json")
    suspend fun getOthersShared(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): ApiCommonResponse<ShareBean>

    // 添加分享
    @POST("lg/user_article/add/json")
    @FormUrlEncoded
    suspend fun addShare(
        @Field("title") title: String, @Field("link") link: String
    ): ApiCommonResponse<ShareBean>
}