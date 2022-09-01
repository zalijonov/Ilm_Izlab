package uz.alijonovz.ilmizlab.screen.center.rating

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.ActivityRateBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.center.CenterModel
import uz.alijonovz.ilmizlab.model.center.request.MakeRatingModel
import uz.alijonovz.ilmizlab.screen.auth.LoginActivity
import uz.alijonovz.ilmizlab.utils.PrefUtils

class RateActivity : AppCompatActivity() {
    lateinit var item: CenterModel
    lateinit var binding: ActivityRateBinding
    var rating = 5.0
    var comment = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        item = intent.getSerializableExtra("extra_center_rate") as CenterModel

        binding.tvTitle.text = item.name

        if(PrefUtils.getToken().isNullOrEmpty()){
            binding.btnRate.visibility = View.GONE
            binding.comment.visibility = View.VISIBLE
            binding.btnSign.visibility = View.VISIBLE
        } else {
            binding.comment.visibility = View.GONE
            binding.btnRate.visibility = View.VISIBLE
            binding.btnSign.visibility = View.GONE
        }

        binding.btnSign.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnRate.setOnClickListener {
            rating = binding.ratingBar.rating.toDouble()
            comment = binding.edtComment.text.toString()
            makeRating(rating, comment, item.id)
            finish()
        }

        binding.back.setOnClickListener {
            finish()
        }
    }

    fun makeRating(rating: Double, comment:String, center_id:Int){
        ApiService.apiClient().makeRating(MakeRatingModel(rating, comment, center_id)).enqueue(object: Callback<BaseResponse<Any>>{
            override fun onResponse(
                call: Call<BaseResponse<Any>>,
                response: Response<BaseResponse<Any>>
            ) {
                if(response.isSuccessful){
                    Toast.makeText(this@RateActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(this@RateActivity, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                Toast.makeText(this@RateActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }
}