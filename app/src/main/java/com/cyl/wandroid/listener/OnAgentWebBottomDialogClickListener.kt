package com.cyl.wandroid.listener

interface OnAgentWebBottomDialogClickListener {
    fun onCollectionClick() // 加入/取消收藏
    fun refreshWebPage() // 刷新网页
    fun copyLink() // 复制链接
    fun openInBrowser() // 从浏览器打开
    fun onShare() // 分享
}