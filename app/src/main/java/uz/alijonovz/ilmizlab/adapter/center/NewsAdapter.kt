package uz.alijonovz.ilmizlab.adapter.center

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import uz.alijonovz.ilmizlab.adapter.BaseAdapter
import uz.alijonovz.ilmizlab.databinding.NewsItemLayoutBinding
import uz.alijonovz.ilmizlab.model.center.NewsModel
import uz.alijonovz.ilmizlab.screen.news.NewsContentActivity
import uz.alijonovz.ilmizlab.utils.Constants

class NewsAdapter(val items: List<NewsModel>) : BaseAdapter<NewsItemLayoutBinding>(items) {

    override fun getBinding(parent: ViewGroup): NewsItemLayoutBinding {
        return NewsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(
        holder: BaseAdapter<NewsItemLayoutBinding>.ItemHolder<NewsItemLayoutBinding>,
        position: Int
    ) {
        val item = items[position]

        holder.binding.tvTitle.text = item.title
        holder.binding.tvCenter.text = item.center_name
        holder.binding.tvDate.text = item.date
        holder.binding.tvAddress.text = item.district_name

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, NewsContentActivity::class.java)
            intent.putExtra("extra_news", item)
            it.context.startActivity(intent)
        }

        Glide.with(holder.binding.root).load(Constants.IMAGE_URL + item.image)
            .into(holder.binding.imgNew)
        Glide.with(holder.binding.root).load(Constants.IMAGE_URL + item.center_image)
            .into(holder.binding.imgCenter)
    }

    override fun initItemData(item: Any) {

    }
}