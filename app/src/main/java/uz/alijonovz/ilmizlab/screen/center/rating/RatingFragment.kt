package uz.alijonovz.ilmizlab.screen.center.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.adapter.center.RatingAdapter
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.FragmentRatingBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.center.RatingModel
import uz.alijonovz.ilmizlab.screen.BaseFragment
import uz.alijonovz.ilmizlab.screen.MainViewModel

class RatingFragment : BaseFragment<FragmentRatingBinding>() {
    var item = 0
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        item = arguments?.getInt("extra_rating")!!
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRatingBinding {
        return FragmentRatingBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        viewModel.error.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.commentData.observe(requireActivity()) {
            binding.recyclerComment.layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerComment.adapter = RatingAdapter(it)
        }

    }

    override fun loadData() {
        viewModel.loadComments(item)
    }

    override fun loadUpdate() {

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            RatingFragment()
    }
}
