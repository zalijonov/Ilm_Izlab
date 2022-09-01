package uz.alijonovz.ilmizlab.screen.center

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.adapter.center.TeachersAdapter
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.ActivityTeachersBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.center.TeacherModel
import uz.alijonovz.ilmizlab.screen.MainViewModel

class TeachersActivity : AppCompatActivity() {
    var id = 0
    lateinit var binding: ActivityTeachersBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeachersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.teacherData.observe(this) {
            binding.recyclerTeacher.layoutManager = LinearLayoutManager(this)
            binding.recyclerTeacher.adapter = TeachersAdapter(it)
        }
        viewModel.error.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }

        id = intent.getIntExtra("extra_teacher", id)
        viewModel.loadTeacher(id)
    }

}