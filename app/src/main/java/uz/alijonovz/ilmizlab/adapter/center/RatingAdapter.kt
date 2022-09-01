package uz.alijonovz.ilmizlab.adapter.center

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.alijonovz.ilmizlab.databinding.RatingItemLayoutBinding
import uz.alijonovz.ilmizlab.model.center.RatingModel
import uz.alijonovz.ilmizlab.utils.Constants

class RatingAdapter(val items: List<RatingModel>) :
    RecyclerView.Adapter<RatingAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: RatingItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            RatingItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.binding.tvComment.text = item.comment
        holder.binding.tvAuthor.text = item.user_fullname
        holder.binding.tvDate.text = item.date
        holder.binding.ratingBar.rating = item.rating.toFloat()

        Glide.with(holder.binding.root).load(Constants.IMAGE_URL + item.user_avatar)
            .into(holder.binding.imgAvatar)
    }

    override fun getItemCount(): Int = items.count()
}