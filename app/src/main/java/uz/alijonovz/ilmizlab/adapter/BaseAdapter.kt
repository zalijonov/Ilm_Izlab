package uz.alijonovz.ilmizlab.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T: ViewBinding>(var items1: List<Any>): RecyclerView.Adapter<BaseAdapter<T>.ItemHolder<T>>() {

    abstract fun getBinding(parent: ViewGroup): T
    abstract fun initItemData(item: Any)
    inner class ItemHolder<B: ViewBinding>(val binding: B): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder<T>{
        return ItemHolder(getBinding(parent))
    }

    override fun onBindViewHolder(holder: ItemHolder<T>, position: Int) {
        initItemData(items1[position])
    }

    override fun getItemCount(): Int {
        return items1.count()
    }
}