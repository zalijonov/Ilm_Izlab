package uz.alijonovz.ilmizlab.screen.center.rating

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import uz.alijonovz.ilmizlab.databinding.ActivityRateBinding
import uz.alijonovz.ilmizlab.model.center.CenterModel
import uz.alijonovz.ilmizlab.screen.BaseActivity
import uz.alijonovz.ilmizlab.screen.MainViewModel
import uz.alijonovz.ilmizlab.screen.auth.LoginActivity
import uz.alijonovz.ilmizlab.utils.PrefUtils

class RateActivity : BaseActivity<ActivityRateBinding>() {
    lateinit var item: CenterModel
    lateinit var viewModel: MainViewModel
    var rating = 5.0
    var comment = ""

    override fun getViewBinding(): ActivityRateBinding {
        return ActivityRateBinding.inflate(layoutInflater)
    }

    override fun initView() {

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        item = intent.getSerializableExtra("extra_center_rate") as CenterModel

        binding.tvTitle.text = item.name

        if (PrefUtils.getToken().isNullOrEmpty()) {
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

        viewModel.progress.observe(this) {

        }
        viewModel.ratingData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.error.observe(this) {
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

    override fun loadData() {

    }

    override fun updateData() {

    }

}