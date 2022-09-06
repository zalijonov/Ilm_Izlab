package uz.alijonovz.ilmizlab.screen.main.subscribed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import uz.alijonovz.ilmizlab.adapter.center.CenterAdapter
import uz.alijonovz.ilmizlab.databinding.FragmentSubscribedBinding
import uz.alijonovz.ilmizlab.model.center.CenterModel
import uz.alijonovz.ilmizlab.model.request.GetCenterByIdRequest
import uz.alijonovz.ilmizlab.screen.BaseFragment
import uz.alijonovz.ilmizlab.screen.MainViewModel
import uz.alijonovz.ilmizlab.utils.PrefUtils

class SubscribedFragment : BaseFragment<FragmentSubscribedBinding>() {
    lateinit var viewModel: MainViewModel
    var checkSubs = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSubscribedBinding {
        return FragmentSubscribedBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        viewModel.progress.observe(requireActivity()){
            binding.swipe.isRefreshing = it
        }

        viewModel.centerData.observe(requireActivity()) {
            val list = mutableListOf<CenterModel>()
            it.forEach { center ->
                checkSubs = false
//                checkSubscriber(center.id)
                if(checkSubs)
                    list += center
            }
            binding.recSubscribed.layoutManager = GridLayoutManager(requireActivity(), 2)
            binding.recSubscribed.adapter = CenterAdapter(list)
        }

        binding.swipe.setOnRefreshListener {
            loadData()
        }

        if(PrefUtils.getToken().isNullOrEmpty()){
            binding.logPlease.visibility = View.VISIBLE
            binding.recSubscribed.visibility = View.GONE
        } else{
            binding.logPlease.visibility = View.GONE
            binding.recSubscribed.visibility = View.VISIBLE
            viewModel.centerData.observe(requireActivity()) {
                val list = mutableListOf<CenterModel>()
                it.forEach { center ->
                    checkSubs = false
//                    checkSubscriber(center.id)
                    if(checkSubs)
                        list += center
                }
                binding.recSubscribed.layoutManager = GridLayoutManager(requireActivity(), 2)
                binding.recSubscribed.adapter = CenterAdapter(list)
            }
        }
    }


    override fun loadData(){
        viewModel.loadCenters(GetCenterByIdRequest())
    }

    override fun loadUpdate() {

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SubscribedFragment()
    }


}