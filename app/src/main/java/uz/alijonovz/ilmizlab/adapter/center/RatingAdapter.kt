package uz.alijonovz.ilmizlab.adapter.center

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import uz.alijonovz.ilmizlab.adapter.BaseAdapter
import uz.alijonovz.ilmizlab.databinding.RatingItemLayoutBinding
import uz.alijonovz.ilmizlab.model.center.RatingModel
import uz.alijonovz.ilmizlab.utils.Constants

class RatingAdapter(val items: List<RatingModel>) : BaseAdapter<RatingItemLayoutBinding>(items) {

    override fun getBinding(parent: ViewGroup): RatingItemLayoutBinding {
        return RatingItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(
        holder: BaseAdapter<RatingItemLayoutBinding>.ItemHolder<RatingItemLayoutBinding>,
        position: Int
    ) {
        val item = items[position]

        holder.binding.tvComment.text = item.comment
        holder.binding.tvAuthor.text = item.user_fullname
        holder.binding.tvDate.text = item.date
        holder.binding.ratingBar.rating = item.rating.toFloat()

        Glide.with(holder.binding.root).load(Constants.IMAGE_URL + item.user_avatar)
            .into(holder.binding.imgAvatar)
    }

    override fun initItemData(item: Any) {

    }
}