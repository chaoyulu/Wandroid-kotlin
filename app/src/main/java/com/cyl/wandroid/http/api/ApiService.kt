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
    @POST("lg/collect/{id}/json")
    suspend fun collectArticle(@Path("id") id: Int): ApiCommonResponse<Any>

    // 从文章列表取消收藏
    @POST("lg/uncollect_originId/{id}/json")
    suspend fun cancelCollectFromArticleList(@Path("id") id: Int): ApiCommonResponse<Any>

    // 从收藏列表取消收藏
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    suspend fun cancelCollectFromCollectionList(
        @Path("id") id: Int,
        @Field("originId") originId: Int
    ): ApiCommonResponse<Any>

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

    // 删除我的分享
    @POST("lg/user_article/delete/{id}/json")
    suspend fun deleteMyShare(@Path("id") id: Int): ApiCommonResponse<Any>

    // 获取TODO列表
    @GET("lg/todo/v2/list/{page}/json")
    suspend fun getMyTodo(
        @Path("page") page: Int, @Query("status") status: Int, @Query("type") type: Int,
        @Query("priority") priority: Int, @Query("orderby") orderby: Int
    ): ApiCommonResponse<CommonArticleData<TodoBean>>

    // 新增待办
    @POST("lg/todo/add/json")
    @FormUrlEncoded
    suspend fun addMyTodo(
        @Field("title") title: String,
        @Field("content") content: String,
        @Field("date") date: String,
        @Field("type") type: Int,
        @Field("priority") priority: Int
    ): ApiCommonResponse<TodoBean>

    // 更新待办
    @POST("lg/todo/update/{id}/json")
    @FormUrlEncoded
    suspend fun updateMyTodo(
        @Path("id") id: Int, @Field("title") title: String, @Field("content") content: String,
        @Field("date") date: String, @Field("status") status: Int, @Field("type") type: Int,
        @Field("priority") priority: Int
    ): ApiCommonResponse<TodoBean>

    // 更新待办状态，例如将未完成修改成完成
    @POST("lg/todo/done/{id}/json")
    @FormUrlEncoded
    suspend fun updateMyTodoStatus(
        @Path("id") id: Int,
        @Field("status") status: Int
    ): ApiCommonResponse<TodoBean>

    // 删除待办
    @POST("lg/todo/delete/{id}/json")
    suspend fun deleteMyTodo(@Path("id") id: Int): ApiCommonResponse<Any>


    @GET("wenda/list/{page}/json")
    suspend fun getQA(@Path("page") page: Int): ApiCommonResponse<CommonArticleData<ArticleBean>>

    // 更新待办状态，例如将未完成修改成完成
    @POST("article/query/{page}/json")
    @FormUrlEncoded
    suspend fun search(
        @Path("page") page: Int,
        @Field("k") k: String
    ): ApiCommonResponse<CommonArticleData<ArticleBean>>

    @GET("hotkey/json")
    suspend fun getHotKey(): ApiCommonResponse<List<HotKeyBean>>
}