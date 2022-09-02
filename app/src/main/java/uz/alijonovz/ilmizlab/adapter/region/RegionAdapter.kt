package uz.alijonovz.ilmizlab.adapter.region

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.alijonovz.ilmizlab.R
import uz.alijonovz.ilmizlab.adapter.BaseAdapter
import uz.alijonovz.ilmizlab.databinding.ListItemLayoutBinding
import uz.alijonovz.ilmizlab.model.region.DistrictModel
import uz.alijonovz.ilmizlab.model.region.RegionModel
import uz.alijonovz.ilmizlab.utils.PrefUtils

interface RegionAdapterListener {
    fun onClick(regionId: Int, districtId: Int, regionName: String)
}

class RegionAdapter(val items: List<RegionModel>, val callback: RegionAdapterListener) :
    BaseAdapter<ListItemLayoutBinding>(items) {

    override fun getBinding(parent: ViewGroup): ListItemLayoutBinding {
        return ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(
        holder: BaseAdapter<ListItemLayoutBinding>.ItemHolder<ListItemLayoutBinding>,
        position: Int
    ) {
        val item = items[position]
        holder.binding.tvList.text = item.name_uz
        val regionName = items[position].name_uz
        if (item.id == PrefUtils.getRegion()) {
            item.checked = true
            holder.binding.subList.visibility = View.VISIBLE
            holder.binding.imgArrow.setImageResource(R.drawable.ic_arrow_up)
        }
        holder.binding.subList.visibility = if (item.checked) View.VISIBLE else View.GONE
        holder.binding.imgArrow.setImageResource(if (holder.binding.subList.visibility == View.GONE) R.drawable.ic_down_drop else R.drawable.ic_arrow_up)
        if (item.checked) {
            holder.binding.subList.adapter =
                DistrictAdapter(item.districts, object : DistrictAdapterListener {
                    override fun onClick(item: DistrictModel) {
                        items.forEach {
                            it.districts.forEach { d ->
                                d.checked = false
                            }
                        }
                        callback.onClick(item.region_id, item.id, regionName)
                        PrefUtils.setRegionId(item.region_id)
                        PrefUtils.setDistrictId(item.id)
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
                PrefUtils.setRegionId(item.id)
                holder.binding.subList.adapter =
                    DistrictAdapter(item.districts, object : DistrictAdapterListener {
                        override fun onClick(item: DistrictModel) {
                            items.forEach {
                                it.districts.forEach { d ->
                                    d.checked = false
                                }
                            }
                            callback.onClick(item.region_id, item.id, regionName)
                            PrefUtils.setRegionId(item.region_id)
                            item.checked = true
                            notifyDataSetChanged()
                        }
                    })
            }
        }
    }

    override fun initItemData(item: Any) {

    }
}