package uz.alijonovz.ilmizlab.adapter.region

import android.view.LayoutInflater
import android.view.ViewGroup
import uz.alijonovz.ilmizlab.adapter.BaseAdapter
import uz.alijonovz.ilmizlab.databinding.RadioItemLayoutBinding
import uz.alijonovz.ilmizlab.model.region.DistrictModel
import uz.alijonovz.ilmizlab.utils.PrefUtils

interface DistrictAdapterListener {
    fun onClick(item: DistrictModel)
}

class DistrictAdapter(val items: List<DistrictModel>, val callback: DistrictAdapterListener) :
    BaseAdapter<RadioItemLayoutBinding>(items) {

    override fun getBinding(parent: ViewGroup): RadioItemLayoutBinding {
        return RadioItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(
        holder: BaseAdapter<RadioItemLayoutBinding>.ItemHolder<RadioItemLayoutBinding>,
        position: Int
    ) {
        val item = items[position]
        if (item.id == PrefUtils.getDistrictId()) {
            item.checked = true
            holder.binding.radioDistrict.isChecked = item.checked
        }
        holder.binding.radioDistrict.text = item.name_uz
        holder.binding.radioDistrict.isChecked = item.checked
        holder.binding.radioDistrict.setOnClickListener {
            callback.onClick(item)
            PrefUtils.setDistrictId(item.id)
            PrefUtils.setRegionId(item.region_id)
            holder.binding.radioDistrict.isChecked != holder.binding.radioDistrict.isChecked
        }
    }

    override fun initItemData(item: Any) {

    }
}