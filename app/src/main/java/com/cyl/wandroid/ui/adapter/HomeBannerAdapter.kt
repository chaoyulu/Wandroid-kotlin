package com.cyl.wandroid.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.cyl.wandroid.R
import com.cyl.wandroid.ext.loadImage
import com.cyl.wandroid.http.bean.HomeBannerBean
import com.youth.banner.adapter.BannerAdapter

class HomeBannerAdapter(banners: List<HomeBannerBean>?) :
    BannerAdapter<HomeBannerBean, HomeBannerAdapter.BannerViewHolder>(banners) {

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {
        val itemView: View =
            LayoutInflater.from(parent?.context).inflate(R.layout.item_home_banner, parent, false)
        return BannerViewHolder(itemView)
    }

    class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onBindView(
        holder: BannerViewHolder,
        data: HomeBannerBean,
        position: Int,
        size: Int
    ) {
        holder.imageView.loadImage(data.imagePath)
    }
}