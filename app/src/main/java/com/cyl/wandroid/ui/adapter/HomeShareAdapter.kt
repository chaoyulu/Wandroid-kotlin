package com.cyl.wandroid.ui.adapter

import android.text.TextUtils
import androidx.core.view.isVisible
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R
import com.cyl.wandroid.http.bean.ArticleBean
import kotlinx.android.synthetic.main.item_home_article.view.*

class HomeShareAdapter(layoutResId: Int = R.layout.item_home_share) :
    BaseQuickAdapter<ArticleBean, BaseViewHolder>(layoutResId), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: ArticleBean) {
        holder.itemView.apply {
            tvNew.isVisible = item.fresh
            tvTitle.text = item.title
            tvDate.text = item.niceDate
            if (!TextUtils.isEmpty(item.author)) {
                tvAuthor.text = item.author
            } else if (!TextUtils.isEmpty(item.shareUser)) {
                tvAuthor.text = item.shareUser
            }
        }
    }
}