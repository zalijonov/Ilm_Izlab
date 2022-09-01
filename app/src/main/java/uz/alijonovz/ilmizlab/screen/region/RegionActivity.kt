package uz.alijonovz.ilmizlab.screen.region

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.adapter.region.RegionAdapter
import uz.alijonovz.ilmizlab.adapter.region.RegionAdapterListener
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.ActivityRegionBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.region.RegionIdModel
import uz.alijonovz.ilmizlab.model.region.RegionModel
import uz.alijonovz.ilmizlab.screen.MainViewModel

class RegionActivity : AppCompatActivity() {
    var regionId: Int = 0
    var districtId: Int = 0
    var regionName: String = "Farg'ona"
    lateinit var binding: ActivityRegionBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.regionData.observe(this) {
            binding.recyclerRegion.layoutManager =
                LinearLayoutManager(this@RegionActivity)
            binding.recyclerRegion.adapter = RegionAdapter(
                it,
                object : RegionAdapterListener {
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

        viewModel.progress.observe(this){

        }

        viewModel.error.observe(this){
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        loadData()

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

    fun loadData(){
        viewModel.loadRegion()
    }
}