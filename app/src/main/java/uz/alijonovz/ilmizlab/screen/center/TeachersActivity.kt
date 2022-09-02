package uz.alijonovz.ilmizlab.screen.center

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alijonovz.ilmizlab.adapter.center.TeachersAdapter
import uz.alijonovz.ilmizlab.databinding.ActivityTeachersBinding
import uz.alijonovz.ilmizlab.screen.BaseActivity
import uz.alijonovz.ilmizlab.screen.MainViewModel

class TeachersActivity : BaseActivity<ActivityTeachersBinding>() {
    var id = 0
    lateinit var viewModel: MainViewModel

    override fun getViewBinding(): ActivityTeachersBinding {
        return ActivityTeachersBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.teacherData.observe(this) {
            binding.recyclerTeacher.layoutManager = LinearLayoutManager(this)
            binding.recyclerTeacher.adapter = TeachersAdapter(it)
        }
        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }

        id = intent.getIntExtra("extra_teacher", id)

    }

    override fun loadData() {
        viewModel.loadTeacher(id)
    }

    override fun updateData() {

    }

}