package uz.alijonovz.ilmizlab.screen.main

import uz.alijonovz.ilmizlab.databinding.ActivityInfoBinding
import uz.alijonovz.ilmizlab.screen.BaseActivity

class InfoActivity : BaseActivity<ActivityInfoBinding>() {
    override fun getViewBinding(): ActivityInfoBinding {
        return ActivityInfoBinding.inflate(layoutInflater)
    }

    override fun initView() {
        binding.btnCloseInfo.setOnClickListener {
            finish()
        }
    }

    override fun loadData() {

    }

    override fun updateData() {

    }
}