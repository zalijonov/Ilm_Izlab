package uz.alijonovz.ilmizlab.adapter.category.listactivity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.alijonovz.ilmizlab.R
import uz.alijonovz.ilmizlab.databinding.ListItemLayoutBinding
import uz.alijonovz.ilmizlab.model.category.CategoryModel
import uz.alijonovz.ilmizlab.model.category.Science

interface CategoryListAdapterListener {
    fun onClick(categoryId: Int, scienceId: Int, categoryName: String)
}

class CategoryListAdapter(
    val items: List<CategoryModel>,
    val callback: CategoryListAdapterListener
) : RecyclerView.Adapter<CategoryListAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: ListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.tvList.text = item.title
        val categoryName = items[position].title
        holder.binding.subList.visibility = if (item.checked) View.VISIBLE else View.GONE
        holder.binding.imgArrow.setImageResource(if (holder.binding.subList.visibility == View.GONE) R.drawable.ic_down_drop else R.drawable.ic_arrow_up)

        if (item.checked) {
            holder.binding.subList.adapter =
                ScienceListAdapter(item.sciences, object : ScienceListAdapterListener {
                    override fun onClick(item: Science) {
                        items.forEach {
                            it.sciences.forEach { d ->
                                d.checked = false
                            }
                        }
                        callback.onClick(item.category_id, item.id, categoryName)
                        item.checked = true
                        notifyDataSetChanged()
                    }
                })
        }

        holder.binding.listLayout.setOnClickListener {
            if (item.checked) {
                holder.binding.subList.visibility = View.GONE
                holder.binding.imgArrow.setImageResource(R.drawable.ic_down_drop)
                item.checked = false
            } else {
                holder.binding.imgArrow.setImageResource(R.drawable.ic_arrow_up)
                holder.binding.subList.visibility = View.VISIBLE
                item.checked = true
                holder.binding.subList.adapter =
                    ScienceListAdapter(item.sciences, object : ScienceListAdapterListener {
                        override fun onClick(item: Science) {
                            items.forEach {
                                it.sciences.forEach { d ->
                                    d.checked = false
                                }
                            }
                            callback.onClick(item.category_id, item.id, categoryName)
                            item.checked = true
                            notifyDataSetChanged()
                        }
                    })
            }
        }
    }

    override fun getItemCount(): Int = items.count()
}