package com.cyl.wandroid.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cyl.wandroid.R
import com.cyl.wandroid.http.bean.ArticleBean
import com.cyl.wandroid.listener.OnSharedChildClickListener
import com.cyl.wandroid.tools.setCollectionImgState
import com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout
import kotlinx.android.synthetic.main.item_home_article.view.ivCollection
import kotlinx.android.synthetic.main.item_home_article.view.tvAuthor
import kotlinx.android.synthetic.main.item_home_article.view.tvDate
import kotlinx.android.synthetic.main.item_home_article.view.tvTitle
import kotlinx.android.synthetic.main.item_share.view.*

class MySharedAdapter(layoutResId: Int = R.layout.item_share) :
    BaseQuickAdapter<ArticleBean, BaseViewHolder>(layoutResId), LoadMoreModule {

    init {
        addChildClickViewIds(R.id.ivCollection)
    }

    private var onSharedChildClickListener: OnSharedChildClickListener? = null

    fun setOnSharedChildClickListener(onSharedChildClickListener: OnSharedChildClickListener) {
        this.onSharedChildClickListener = onSharedChildClickListener
    }

    override fun convert(holder: BaseViewHolder, item: ArticleBean) {
        holder.itemView.apply {
            tvTitle.text = item.title
            tvDate.text = item.niceDate
            tvAuthor.text = item.shareUser

            setCollectionImgState(context, ivCollection, item.collect)

            constraintLayout.setOnClickListener {
                val state = EasySwipeMenuLayout.getStateCache()
                if (state == null) {
                    onSharedChildClickListener?.onWholeItemClick(holder.layoutPosition)
                }
                menuLayout.resetStatus()
            }

            fra_delete.setOnClickListener {
                onSharedChildClickListener?.onDeleteClick(holder.layoutPosition)
                menuLayout.resetStatus()
            }
        }
    }
}