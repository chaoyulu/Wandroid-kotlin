package com.cyl.wandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.tools.setCollectionImgState
import kotlinx.android.synthetic.main.item_home_article.view.*

class MySharedAdapter(layoutResId: Int = R.layout.item_collection) :
    BaseQuickAdapter<ArticleBean, BaseViewHolder>(layoutResId), LoadMoreModule {

    init {
        addChildClickViewIds(R.id.ivCollection)
    }

    override fun convert(holder: BaseViewHolder, item: ArticleBean) {
        holder.itemView.apply {
            tvTitle.text = item.title
            tvDate.text = item.niceDate
            tvAuthor.text = item.shareUser

            setCollectionImgState(context, ivCollection, item.collect)
        }
    }
}