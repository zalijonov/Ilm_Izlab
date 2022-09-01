package uz.alijonovz.ilmizlab.screen.region

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

class RegionActivity : AppCompatActivity() {
    var regionId: Int = 0
    var districtId: Int = 0
    var regionName: String = "Farg'ona"
    lateinit var binding: ActivityRegionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadRegion()

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

    private fun loadRegion() {
        ApiService.apiClient().getRegions()
            .enqueue(object : Callback<BaseResponse<List<RegionModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<RegionModel>>>,
                    response: Response<BaseResponse<List<RegionModel>>>
                ) {
                    if (response.body()!!.success) {
                        binding.recyclerRegion.layoutManager =
                            LinearLayoutManager(this@RegionActivity)
                        binding.recyclerRegion.adapter = RegionAdapter(
                            response.body()?.data ?: emptyList(),
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
                    } else Toast.makeText(
                        this@RegionActivity,
                        response.body()!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onFailure(call: Call<BaseResponse<List<RegionModel>>>, t: Throwable) {
                    Toast.makeText(this@RegionActivity, t.localizedMessage, Toast.LENGTH_SHORT)
                        .show()
                }
            })
    }
}