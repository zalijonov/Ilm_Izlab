package uz.alijonovz.ilmizlab.adapter.center

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.alijonovz.ilmizlab.databinding.CenterItemLayoutBinding
import uz.alijonovz.ilmizlab.model.center.CenterModel
import uz.alijonovz.ilmizlab.screen.center.CenterActivity
import uz.alijonovz.ilmizlab.utils.Constants
import java.math.RoundingMode
import java.text.DecimalFormat

class CenterAdapter(val items: List<CenterModel>) :
    RecyclerView.Adapter<CenterAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: CenterItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CenterItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]
        var courseLine = ""

        holder.binding.tvTitle.text = item.name
        holder.binding.tvComment.text = item.comment
        holder.binding.tvLocation.text =
            "${item.region.region_name}, ${item.district.district_name}, ${item.address}"

        item.courses.forEach {
            courseLine += it.name + ", "
        }

        if(courseLine.isNullOrEmpty()){
            holder.binding.courseList.visibility = View.GONE
        } else {
            holder.binding.courseList.text = courseLine
        }

        holder.binding.root.setOnClickListener {
            val intent = Intent(it.context, CenterActivity::class.java)
            intent.putExtra("center_extra", item)
            it.context.startActivity(intent)
        }

        var df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.UP
        var s = item.rating.toDoubleOrNull() ?: 0.0
        holder.binding.tvRank.text = df.format(s)

        Glide.with(holder.binding.root).load(Constants.IMAGE_URL + item.main_image)
            .into(holder.binding.mainImg)
    }

    override fun getItemCount(): Int = items.count()
}