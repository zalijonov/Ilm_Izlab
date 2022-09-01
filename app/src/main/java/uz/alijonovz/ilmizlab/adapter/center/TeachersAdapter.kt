package uz.alijonovz.ilmizlab.adapter.center

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.alijonovz.ilmizlab.databinding.TeacherItemLayoutBinding
import uz.alijonovz.ilmizlab.model.center.TeacherModel
import uz.alijonovz.ilmizlab.utils.Constants

class TeachersAdapter(val items: List<TeacherModel>) :
    RecyclerView.Adapter<TeachersAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: TeacherItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            TeacherItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.binding.tvName.text = item.name
        holder.binding.experience.text = item.experience
        holder.binding.specialization.text = item.specialization

        Glide.with(holder.itemView.context).load(Constants.IMAGE_URL + item.avatar)
            .into(holder.binding.imgAvatar)

        holder.binding.tvFeed.setOnClickListener {
            var uri = Uri.parse(item.info_link)
            var intent = Intent(Intent.ACTION_VIEW, uri)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.count()
}