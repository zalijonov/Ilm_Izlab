package uz.alijonovz.ilmizlab.screen.news

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.ActivityNewsContentBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.center.NewsModel
import uz.alijonovz.ilmizlab.screen.MainViewModel
import uz.alijonovz.ilmizlab.utils.Constants

class NewsContentActivity : AppCompatActivity() {
    lateinit var item: NewsModel
    lateinit var binding: ActivityNewsContentBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.error.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.progress.observe(this){

        }

        viewModel.newsContentData.observe(this){
            val data = it

            binding.tvContent.text = data.content
            binding.tvTitle.text = data.title
            Glide.with(this@NewsContentActivity).load(Constants.IMAGE_URL + data.image)
                .into(binding.imgContent)
        }
        binding.back.setOnClickListener {
            finish()
        }

        item = intent.getSerializableExtra("extra_news") as NewsModel
        loadData()
    }

    fun loadData(){
        viewModel.loadNewsContent(item.id)
    }
}