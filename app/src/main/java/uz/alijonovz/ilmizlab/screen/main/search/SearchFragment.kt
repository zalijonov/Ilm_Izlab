package uz.alijonovz.ilmizlab.screen.main.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import uz.alijonovz.ilmizlab.R
import uz.alijonovz.ilmizlab.adapter.center.CenterAdapter
import uz.alijonovz.ilmizlab.databinding.FragmentSearchBinding
import uz.alijonovz.ilmizlab.model.category.CategoryIdModel
import uz.alijonovz.ilmizlab.model.region.RegionIdModel
import uz.alijonovz.ilmizlab.model.request.GetCenterByIdRequest
import uz.alijonovz.ilmizlab.screen.BaseFragment
import uz.alijonovz.ilmizlab.screen.category.CategoryListActivity
import uz.alijonovz.ilmizlab.screen.MainViewModel
import uz.alijonovz.ilmizlab.screen.region.RegionActivity
import uz.alijonovz.ilmizlab.utils.Constants

class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    lateinit var viewModel: MainViewModel
    var regionId: Int = 0
    var districtId: Int = 0
    var regionName: String = "Farg'ona viloyati"
    var search = ""
    var sortType = "rating"
    var categoryId: Int = 0
    var scienceId: Int = 0
    var categoryName: String = "Hammasi"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchBinding {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        return FragmentSearchBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        viewModel.centerData.observe(requireActivity()) {
            binding.recyclerCenter.layoutManager = GridLayoutManager(requireActivity(), 2)
            binding.recyclerCenter.adapter = CenterAdapter(it)
        }

        viewModel.progress.observe(requireActivity()){
            binding.swipe.isRefreshing = it
        }

        binding.swipe.setOnRefreshListener {
            loadData()
        }

        binding.tvRegion.text = regionName
        binding.tvScience.text = categoryName

        if (binding.sortRating.isChecked) {
            binding.tvSort.text = binding.sortRating.text
        } else {
            binding.tvSort.text = binding.sortDistance.text
        }

        binding.edtSearch.addTextChangedListener {
            search = it.toString()
            loadData()
        }

        binding.sortGroup.setOnCheckedChangeListener { group, checkedId ->
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

        binding.cardScience.setOnClickListener {
            startActivity(Intent(requireActivity(), CategoryListActivity::class.java))
        }

        binding.cardRegion.setOnClickListener {
            startActivity(Intent(requireActivity(), RegionActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
        binding.tvRegion.text = regionName
        binding.tvScience.text = if(categoryName.isEmpty()) "Hammasi" else categoryName
    }

    @Subscribe
    fun onEventRegion(regionIdModel: RegionIdModel) {
        this.regionId = regionIdModel.region_id
        this.districtId = regionIdModel.district_id
        this.regionName = regionIdModel.region_name

    }

    @Subscribe
    fun onEventCategory(category: CategoryIdModel) {
        this.categoryId = category.category_id
        this.scienceId = category.science_id
        this.categoryName = category.category_name
    }

    override fun loadData() {
        viewModel.loadCenters(
            GetCenterByIdRequest(
                region_id = regionId,
                district_id = districtId,
                keyword = search,
                sort = sortType,
                category_id = categoryId,
                science_id = scienceId,
                longitude = Constants.currentLongitude,
                latitude = Constants.currentLatitude
            )
        )
    }

    override fun loadUpdate() {

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment()
    }
}