package uz.alijonovz.ilmizlab.adapter.region

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.alijonovz.ilmizlab.databinding.RadioItemLayoutBinding
import uz.alijonovz.ilmizlab.model.region.DistrictModel

interface DistrictAdapterListener {
    fun onClick(item: DistrictModel)
}

class DistrictAdapter(val items: List<DistrictModel>, val callback: DistrictAdapterListener) :
    RecyclerView.Adapter<DistrictAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: RadioItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            RadioItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        holder.binding.radioDistrict.text = item.name_uz
        holder.binding.radioDistrict.isChecked = item.checked
        holder.binding.radioDistrict.setOnClickListener {
            callback.onClick(item)
            holder.binding.radioDistrict.isChecked != holder.binding.radioDistrict.isChecked
        }
    }

    override fun getItemCount(): Int = items.count()
}