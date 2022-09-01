package uz.alijonovz.ilmizlab.screen.main.subscribed

import android.graphics.Color
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.R
import uz.alijonovz.ilmizlab.adapter.center.CenterAdapter
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.FragmentSubscribedBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.center.CenterModel
import uz.alijonovz.ilmizlab.model.request.GetCenterByIdRequest
import uz.alijonovz.ilmizlab.screen.main.MainViewModel
import uz.alijonovz.ilmizlab.utils.PrefUtils

class SubscribedFragment : Fragment() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentSubscribedBinding
    var checkSubs = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubscribedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        loadData()

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
            loadData()
        }
    }

    fun loadData(){
        viewModel.loadCenters(GetCenterByIdRequest())
    }

//    fun checkSubscriber(centerId: Int) {
//        ApiService.apiClient().checkSubscriber(centerId).enqueue(object :
//            Callback<BaseResponse<Boolean>> {
//            override fun onResponse(
//                call: Call<BaseResponse<Boolean>>,
//                response: Response<BaseResponse<Boolean>>
//            ) {
////                checkSubs = response.body()?.data ?: false
//            }
//
//            override fun onFailure(call: Call<BaseResponse<Boolean>>, t: Throwable) {
//                Toast.makeText(requireActivity(), t.localizedMessage, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SubscribedFragment()
    }


        }