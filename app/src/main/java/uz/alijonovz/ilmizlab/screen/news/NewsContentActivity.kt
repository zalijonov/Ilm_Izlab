package uz.alijonovz.ilmizlab.screen.news

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.ActivityNewsContentBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.center.NewsModel
import uz.alijonovz.ilmizlab.utils.Constants

class NewsContentActivity : AppCompatActivity() {
    lateinit var item: NewsModel
    lateinit var binding: ActivityNewsContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
        }

        item = intent.getSerializableExtra("extra_news") as NewsModel
        loadContent()
    }

    fun loadContent() {
        ApiService.apiClient().getNewsContent(item.id)
            .enqueue(object : Callback<BaseResponse<NewsModel>> {
                override fun onResponse(
                    call: Call<BaseResponse<NewsModel>>,
                    response: Response<BaseResponse<NewsModel>>
                ) {
                    if (response.body()!!.success) {
                        val data = response.body()!!.data

                        binding.tvContent.text = data.content
                        binding.tvTitle.text = data.title
                        Glide.with(this@NewsContentActivity).load(Constants.IMAGE_URL + data.image)
                            .into(binding.imgContent)
                    }
                }

                override fun onFailure(call: Call<BaseResponse<NewsModel>>, t: Throwable) {

                }

            })
    }
}