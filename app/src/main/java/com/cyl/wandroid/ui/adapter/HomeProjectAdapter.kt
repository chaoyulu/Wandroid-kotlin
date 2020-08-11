package com.cyl.wandroid.ui.adapter

import android.text.TextUtils
import androidx.core.view.isVisible
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R
import com.cyl.wandroid.ext.loadImage
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.tools.setCollectionImgState
import kotlinx.android.synthetic.main.item_home_article.view.tvAuthor
import kotlinx.android.synthetic.main.item_home_article.view.tvCategory
import kotlinx.android.synthetic.main.item_home_article.view.tvDate
import kotlinx.android.synthetic.main.item_home_article.view.tvLabel
import kotlinx.android.synthetic.main.item_home_article.view.tvLabel2
import kotlinx.android.synthetic.main.item_home_article.view.tvTitle
import kotlinx.android.synthetic.main.item_home_project.view.*

class HomeProjectAdapter(layoutResId: Int = R.layout.item_home_project) :
    BaseQuickAdapter<ArticleBean, BaseViewHolder>(layoutResId), LoadMoreModule {
    override fun convert(holder: BaseViewHolder, item: ArticleBean) {
        holder.itemView.apply {
            tvTitle.text = item.title
            tvDate.text = item.niceDate
            if (!TextUtils.isEmpty(item.author)) {
                tvAuthor.text = item.author
            } else if (!TextUtils.isEmpty(item.shareUser)) {
                tvAuthor.text = item.shareUser
            }

            // 标签
            val tags = item.tags
            when {
                tags.isEmpty() -> {
                    tvLabel.isVisible = false
                    tvLabel2.isVisible = false
                }
                tags.size == 1 -> {
                    tvLabel.isVisible = true
                    tvLabel2.isVisible = false
                    tvLabel.text = tags[0].name
                }
                tags.size == 2 -> {
                    tvLabel.isVisible = true
                    tvLabel2.isVisible = true
                    tvLabel.text = tags[0].name
                    tvLabel2.text = tags[1].name
                }
            }
            // 分类
            tvCategory.text = when {
                !TextUtils.isEmpty(item.superChapterName) && !TextUtils.isEmpty(item.chapterName) -> item.superChapterName
                !TextUtils.isEmpty(item.superChapterName) && TextUtils.isEmpty(item.chapterName) -> item.superChapterName
                TextUtils.isEmpty(item.superChapterName) && !TextUtils.isEmpty(item.chapterName) -> item.chapterName
                else -> ""
            }

            tvDesc.text = item.desc
            ivProjectImage.loadImage(item.envelopePic)

            setCollectionImgState(context, ivCollection, item.collect)
        }
    }
}