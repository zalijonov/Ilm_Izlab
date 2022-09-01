package uz.alijonovz.ilmizlab.screen.center

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.adapter.center.TeachersAdapter
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.ActivityTeachersBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.center.TeacherModel

class TeachersActivity : AppCompatActivity() {
    var id = 0
    lateinit var binding: ActivityTeachersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeachersBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        id = intent.getIntExtra("extra_teacher", id)
        loadTeachers()
    }

    fun loadTeachers(){
        ApiService.apiClient().getTeachers(id).enqueue(object: Callback<BaseResponse<List<TeacherModel>>>{
            override fun onResponse(
                call: Call<BaseResponse<List<TeacherModel>>>,
                response: Response<BaseResponse<List<TeacherModel>>>
            ) {
                if(response.body()!!.success){
                    binding.recyclerTeacher.layoutManager = LinearLayoutManager(this@TeachersActivity)
                    binding.recyclerTeacher.adapter = TeachersAdapter(response.body()?.data ?: emptyList())
                } else
                    Toast.makeText(this@TeachersActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<BaseResponse<List<TeacherModel>>>, t: Throwable) {
                Toast.makeText(this@TeachersActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }
}