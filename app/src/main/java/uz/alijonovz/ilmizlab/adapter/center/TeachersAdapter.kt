package uz.alijonovz.ilmizlab.adapter.center

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import uz.alijonovz.ilmizlab.adapter.BaseAdapter
import uz.alijonovz.ilmizlab.databinding.TeacherItemLayoutBinding
import uz.alijonovz.ilmizlab.model.center.TeacherModel
import uz.alijonovz.ilmizlab.utils.Constants

class TeachersAdapter(val items: List<TeacherModel>) :
    BaseAdapter<TeacherItemLayoutBinding>(items) {

    override fun getBinding(parent: ViewGroup): TeacherItemLayoutBinding {
        return TeacherItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    }

    override fun onBindViewHolder(
        holder: BaseAdapter<TeacherItemLayoutBinding>.ItemHolder<TeacherItemLayoutBinding>,
        position: Int
    ) {
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

    override fun initItemData(item: Any) {

    }
}