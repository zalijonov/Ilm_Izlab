package uz.alijonovz.ilmizlab.adapter.category.listactivity

import android.view.LayoutInflater
import android.view.ViewGroup
import uz.alijonovz.ilmizlab.adapter.BaseAdapter
import uz.alijonovz.ilmizlab.databinding.RadioItemLayoutBinding
import uz.alijonovz.ilmizlab.model.category.Science
import uz.alijonovz.ilmizlab.utils.PrefUtils

interface ScienceListAdapterListener {
    fun onClick(item: Science)
}

class ScienceListAdapter(val items: List<Science>, val callback: ScienceListAdapterListener) :
    BaseAdapter<RadioItemLayoutBinding>(items) {

    override fun getBinding(parent: ViewGroup): RadioItemLayoutBinding {
        return RadioItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(
        holder: BaseAdapter<RadioItemLayoutBinding>.ItemHolder<RadioItemLayoutBinding>,
        position: Int
    ) {
        val item = items[position]

        if (item.id == PrefUtils.getScienceId()) {
            item.checked = true
            holder.binding.radioDistrict.isChecked = item.checked
        }

        holder.binding.radioDistrict.text = item.title
        holder.binding.radioDistrict.isChecked = item.checked
        holder.binding.radioDistrict.setOnClickListener {
            callback.onClick(item)
            PrefUtils.setCategoryId(item.category_id)
            PrefUtils.setScienceId(item.id)
            holder.binding.radioDistrict.isChecked != holder.binding.radioDistrict.isChecked
        }
    }

    override fun initItemData(item: Any) {

    }
}