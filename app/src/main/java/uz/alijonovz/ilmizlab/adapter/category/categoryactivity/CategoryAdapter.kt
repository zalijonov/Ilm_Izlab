package uz.alijonovz.ilmizlab.adapter.category.categoryactivity


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import uz.alijonovz.ilmizlab.adapter.BaseAdapter
import uz.alijonovz.ilmizlab.databinding.CategoryItemLayoutBinding
import uz.alijonovz.ilmizlab.model.category.CategoryModel
import uz.alijonovz.ilmizlab.screen.category.CategoryActivity
import uz.alijonovz.ilmizlab.utils.Constants

class CategoryAdapter(val items: List<CategoryModel>) :
    BaseAdapter<CategoryItemLayoutBinding>(items) {

    override fun getBinding(parent: ViewGroup): CategoryItemLayoutBinding {
        return CategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(
        holder: BaseAdapter<CategoryItemLayoutBinding>.ItemHolder<CategoryItemLayoutBinding>,
        position: Int
    ) {
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

    override fun initItemData(item: Any) {

    }

}