package uz.alijonovz.ilmizlab.screen.center.rating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.adapter.center.RatingAdapter
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.FragmentRatingBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.center.RatingModel

class RatingFragment : Fragment() {
    var item = 0
    lateinit var binding: FragmentRatingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = arguments?.getInt("extra_rating")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRatingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadComments(item)
    }

    private fun loadComments(id: Int) {
        ApiService.apiClient().getRatings(id)
            .enqueue(object : Callback<BaseResponse<List<RatingModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<RatingModel>>>,
                    response: Response<BaseResponse<List<RatingModel>>>
                ) {
                    if (response.body()!!.success) {
                        binding.recyclerComment.layoutManager =
                            LinearLayoutManager(requireActivity())
                        binding.recyclerComment.adapter =
                            RatingAdapter(response.body()?.data ?: emptyList())
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<List<RatingModel>>>, t: Throwable) {
                    Toast.makeText(requireActivity(), t.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            })
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            RatingFragment()
    }
}
