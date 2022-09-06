package uz.alijonovz.ilmizlab.screen.region

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.greenrobot.eventbus.EventBus
import uz.alijonovz.ilmizlab.adapter.region.RegionAdapter
import uz.alijonovz.ilmizlab.adapter.region.RegionAdapterListener
import uz.alijonovz.ilmizlab.databinding.ActivityRegionBinding
import uz.alijonovz.ilmizlab.model.region.RegionIdModel
import uz.alijonovz.ilmizlab.screen.BaseActivity
import uz.alijonovz.ilmizlab.screen.MainViewModel
import uz.alijonovz.ilmizlab.utils.PrefUtils

class RegionActivity : BaseActivity<ActivityRegionBinding>() {
    var regionId: Int = PrefUtils.getRegion()
    var districtId: Int = PrefUtils.getDistrictId()
    var regionName: String = "Farg'ona"
    lateinit var viewModel: MainViewModel

    override fun getViewBinding(): ActivityRegionBinding {
        return ActivityRegionBinding.inflate(layoutInflater)
    }

    override fun initView() {

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.regionData.observe(this) {
            binding.recyclerRegion.layoutManager =
                LinearLayoutManager(this@RegionActivity)
            binding.recyclerRegion.adapter = RegionAdapter(it, object : RegionAdapterListener {
                override fun onClick(
                    regionId: Int,
                    districtId: Int,
                    regionName: String
                ) {
                    this@RegionActivity.regionId = regionId
                    this@RegionActivity.districtId = districtId
                    this@RegionActivity.regionName = regionName
                }

            })
        }

        viewModel.progress.observe(this) {

        }

        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        binding.btnSelect.setOnClickListener {
            val districtModel = RegionIdModel(
                region_id = regionId,
                district_id = districtId,
                region_name = regionName
            )
            EventBus.getDefault().post(districtModel)
            finish()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    override fun loadData() {
        viewModel.loadRegion()
    }

    override fun updateData() {

    }


}