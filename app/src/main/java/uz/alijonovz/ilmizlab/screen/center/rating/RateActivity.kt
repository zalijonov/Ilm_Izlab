package uz.alijonovz.ilmizlab.screen.center.rating

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.ActivityRateBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.center.CenterModel
import uz.alijonovz.ilmizlab.model.center.request.MakeRatingModel
import uz.alijonovz.ilmizlab.screen.MainViewModel
import uz.alijonovz.ilmizlab.screen.auth.LoginActivity
import uz.alijonovz.ilmizlab.utils.PrefUtils

class RateActivity : AppCompatActivity() {
    lateinit var item: CenterModel
    lateinit var binding: ActivityRateBinding
    lateinit var viewModel: MainViewModel
    var rating = 5.0
    var comment = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
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

        viewModel.progress.observe(this){

        }
        viewModel.ratingData.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.error.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        binding.btnRate.setOnClickListener {
            rating = binding.ratingBar.rating.toDouble()
            comment = binding.edtComment.text.toString()
            viewModel.makeRating(rating, comment, item.id)
            finish()
        }

        binding.back.setOnClickListener {
            finish()
        }
    }

}