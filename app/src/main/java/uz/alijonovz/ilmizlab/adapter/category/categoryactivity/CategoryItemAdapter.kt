package uz.alijonovz.ilmizlab.adapter.category.categoryactivity

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import uz.alijonovz.ilmizlab.R
import uz.alijonovz.ilmizlab.adapter.BaseAdapter
import uz.alijonovz.ilmizlab.databinding.CategoryItemLayoutBinding
import uz.alijonovz.ilmizlab.model.category.Science
import uz.alijonovz.ilmizlab.utils.Constants

interface CategoryItemCallback {
    fun onClick(item: Science)
}

class CategoryItemAdapter(val items: List<Science>, val callback: CategoryItemCallback) :
    BaseAdapter<CategoryItemLayoutBinding>(items) {

    override fun getBinding(parent: ViewGroup): CategoryItemLayoutBinding {
        return CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(
        holder: BaseAdapter<CategoryItemLayoutBinding>.ItemHolder<CategoryItemLayoutBinding>,
        position: Int
    ) {
        val item = items[position]

        holder.binding.root.setOnClickListener {
            items.forEach {
                it.checked = false
            }

            item.checked = true
            callback.onClick(item)
            notifyDataSetChanged()
        }

        if (item.checked) {
            holder.binding.tvTitle.setTextColor(Color.WHITE)
            holder.binding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.primaryColor
                )
            )
            holder.binding.itemImage.setColorFilter(Color.WHITE)
        } else {
            holder.binding.tvTitle.setTextColor(Color.BLACK)
            holder.binding.itemImage.setColorFilter(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.primaryColor
                )
            )
            holder.binding.cardView.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
        }
        Glide.with(holder.binding.root).load(Constants.IMAGE_URL + item.icon)
            .into(holder.binding.itemImage)
        holder.binding.tvTitle.text = item.title
    }

    override fun initItemData(item: Any) {

    }
}