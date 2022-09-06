package uz.alijonovz.ilmizlab.screen.news

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import uz.alijonovz.ilmizlab.databinding.ActivityNewsContentBinding
import uz.alijonovz.ilmizlab.model.center.NewsModel
import uz.alijonovz.ilmizlab.screen.BaseActivity
import uz.alijonovz.ilmizlab.screen.MainViewModel
import uz.alijonovz.ilmizlab.utils.Constants

class NewsContentActivity : BaseActivity<ActivityNewsContentBinding>() {
    lateinit var item: NewsModel
    lateinit var viewModel: MainViewModel

    override fun getViewBinding(): ActivityNewsContentBinding {
        return ActivityNewsContentBinding.inflate(layoutInflater)
    }

    override fun initView() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.progress.observe(this) {

        }

        viewModel.newsContentData.observe(this) {
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
    }

    override fun loadData() {
        viewModel.loadNewsContent(item.id)
    }

    override fun updateData() {

    }
}