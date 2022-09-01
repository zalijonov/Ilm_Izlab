package uz.alijonovz.ilmizlab.adapter.center

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.alijonovz.ilmizlab.databinding.CourseItemLayoutBinding
import uz.alijonovz.ilmizlab.model.center.CourseModel
import uz.alijonovz.ilmizlab.screen.center.TeachersActivity

class CourseAdapter(var items: List<CourseModel>) :
    RecyclerView.Adapter<CourseAdapter.ItemHolder>() {

    inner class ItemHolder(val binding: CourseItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            CourseItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.binding.tvCourse.text = item.name
        holder.binding.tvPrice.text = "${item.monthly_payment}/oyiga"
        holder.binding.tvScience.text = item.science.title
        holder.binding.btnTeachers.setOnClickListener {
            var intent = Intent(it.context, TeachersActivity::class.java)
            intent.putExtra("extra_teacher", item.center_id)
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.count()
}