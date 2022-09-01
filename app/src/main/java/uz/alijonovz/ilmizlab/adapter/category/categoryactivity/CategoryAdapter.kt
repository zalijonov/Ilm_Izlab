package uz.alijonovz.ilmizlab.adapter.category.categoryactivity


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.alijonovz.ilmizlab.databinding.CategoryItemLayoutBinding
import uz.alijonovz.ilmizlab.model.category.CategoryModel
import uz.alijonovz.ilmizlab.screen.category.CategoryActivity
import uz.alijonovz.ilmizlab.utils.Constants

class CategoryAdapter(val items: List<CategoryModel>) :
    RecyclerView.Adapter<CategoryAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: CategoryItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CategoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.binding.tvTitle.text = item.title

        Glide.with(holder.binding.root).load(Constants.IMAGE_URL + item.icon)
            .into(holder.binding.itemImage)

        holder.itemView.setOnClickListener {
            val intent = Intent(it.context, CategoryActivity::class.java)
            intent.putExtra("extra_category", item)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.count()
}