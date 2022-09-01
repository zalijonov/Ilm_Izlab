package uz.alijonovz.ilmizlab.screen.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alijonovz.ilmizlab.adapter.category.categoryactivity.CategoryAdapter
import uz.alijonovz.ilmizlab.adapter.center.CenterAdapter
import uz.alijonovz.ilmizlab.adapter.viewpager.ViewPagerAdapter
import uz.alijonovz.ilmizlab.adapter.viewpager.autoScroll
import uz.alijonovz.ilmizlab.databinding.FragmentHomeBinding
import uz.alijonovz.ilmizlab.model.request.GetCenterByIdRequest
import uz.alijonovz.ilmizlab.screen.main.MainViewModel
import uz.alijonovz.ilmizlab.utils.Constants

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.error.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.categoryData.observe(requireActivity()) {
            binding.recyclerCategory.layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.recyclerCategory.adapter = CategoryAdapter(it)
        }

        viewModel.progress.observe(requireActivity()) {
            binding.swipe.isRefreshing = it
        }

        viewModel.centerData.observe(requireActivity()) {
            binding.recyclerTopCenter.layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            binding.recyclerTopCenter.adapter = CenterAdapter(it)
        }

        viewModel.closeCenter.observe(requireActivity()) {
            binding.recyclerCloseCenter.layoutManager =
                GridLayoutManager(requireActivity(), 2)
            binding.recyclerCloseCenter.adapter = CenterAdapter(it)
        }

        viewModel.offerData.observe(requireActivity()) {
            binding.viewPager.adapter = ViewPagerAdapter(requireActivity(), it)
            binding.viewPager.autoScroll(3000)
        }

        binding.swipe.setOnRefreshListener {
            loadData()
        }
        loadData()
    }

    private fun loadData() {
        viewModel.loadCategory()
        viewModel.loadCenters(
            GetCenterByIdRequest(
                sort = "rating", longitude = Constants.currentLongitude,
                latitude = Constants.currentLatitude
            )
        )
        viewModel.loadCloseCenters()
        viewModel.loadOffers()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment()
    }
}