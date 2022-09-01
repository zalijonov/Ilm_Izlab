package uz.alijonovz.ilmizlab.screen.category

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.R
import uz.alijonovz.ilmizlab.adapter.category.categoryactivity.CategoryItemAdapter
import uz.alijonovz.ilmizlab.adapter.category.categoryactivity.CategoryItemCallback
import uz.alijonovz.ilmizlab.adapter.center.CenterAdapter
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.ActivityCategoryBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.category.CategoryModel
import uz.alijonovz.ilmizlab.model.category.Science
import uz.alijonovz.ilmizlab.model.center.CenterModel
import uz.alijonovz.ilmizlab.model.region.RegionIdModel
import uz.alijonovz.ilmizlab.model.request.GetCenterByIdRequest
import uz.alijonovz.ilmizlab.screen.MainViewModel
import uz.alijonovz.ilmizlab.screen.region.RegionActivity

class CategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryBinding
    lateinit var viewModel: MainViewModel
    lateinit var item: CategoryModel
    private var regionId = 0
    private var districtId = 0
    private var regionName: String = "Farg'ona viloyati"
    private var sortType = "rating"
    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.centerData.observe(this) {
            binding.recyclerCenter.layoutManager = GridLayoutManager(this, 2)
            binding.recyclerCenter.adapter = CenterAdapter(it)
        }

        viewModel.progress.observe(this){
            binding.swipe.isRefreshing = it
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        binding.back.setOnClickListener {
            finish()
        }

        binding.swipe.setOnRefreshListener {
            loadData()
            binding.swipe.isRefreshing = false
        }
        if (binding.sortRating.isChecked) {
            binding.tvSort.text = binding.sortRating.text
        } else {
            binding.tvSort.text = binding.sortDistance.text
        }

        binding.sortGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.sortRating) {
                sortType = "rating"
                binding.tvSort.text = binding.sortRating.text
            } else {
                sortType = "distance"
                binding.tvSort.text = binding.sortDistance.text
            }
            loadData()
            binding.layoutSort.visibility = View.GONE
        }
        binding.cardSort.setOnClickListener {
            binding.layoutSort.visibility = View.VISIBLE
        }

        binding.layoutSort.setOnClickListener {
            binding.layoutSort.visibility = View.GONE
        }

        item = intent.getSerializableExtra("extra_category") as CategoryModel
        binding.tvTitle.text = item.title
        binding.categoryItem.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryItem.adapter =
            CategoryItemAdapter(item.sciences, object : CategoryItemCallback {
                override fun onClick(item: Science) {
                    id = item.id
                    loadData()
                }

            })
        binding.tvRegion.text = regionName
        binding.cardRegion.setOnClickListener {
            startActivity(Intent(this, RegionActivity::class.java))
        }
        loadData()
    }

    fun loadData(){
        viewModel.loadCenters(GetCenterByIdRequest(
            region_id = regionId,
            district_id = districtId,
            category_id = item.id,
            science_id = id,
            sort = sortType
        ))
    }

    override fun onResume() {
        super.onResume()
        loadData()
        binding.tvRegion.text = regionName
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    @Subscribe
    fun onEvent(address: RegionIdModel) {
        this.regionId = address.region_id
        this.districtId = address.district_id
        this.regionName = address.region_name
    }

}