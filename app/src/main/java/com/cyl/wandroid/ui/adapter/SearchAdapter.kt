package com.cyl.wandroid.ui.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R
import com.cyl.wandroid.ext.makeTextHighlightForMultiKeys
import com.cyl.wandroid.ext.toSearchTitleColorString
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.tools.setCollectionImgState
import kotlinx.android.synthetic.main.item_home_article.view.*

class SearchAdapter(layoutResId: Int = R.layout.item_collection) :
    BaseQuickAdapter<ArticleBean, BaseViewHolder>(layoutResId), LoadMoreModule {

    private var key: String = ""
    fun setSearchKey(key: String) {
        this.key = key
    }

    init {
        addChildClickViewIds(R.id.ivCollection)
    }

    override fun convert(holder: BaseViewHolder, item: ArticleBean) {
        holder.itemView.apply {
            tvTitle.text = item.title.toSearchTitleColorString()
            tvDate.text = item.niceDate

            if (!TextUtils.isEmpty(item.author)) {
                tvAuthor.text = item.author.makeTextHighlightForMultiKeys(key)
            } else if (!TextUtils.isEmpty(item.shareUser)) {
                tvAuthor.text = item.shareUser.makeTextHighlightForMultiKeys(key)
            }

            setCollectionImgState(context, ivCollection, item.collect)
        }
    }
}