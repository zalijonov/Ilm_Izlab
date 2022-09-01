package uz.alijonovz.ilmizlab.screen.center.news

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
import uz.alijonovz.ilmizlab.adapter.center.NewsAdapter
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.FragmentNewsCenterBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.center.NewsModel

class NewsCenterFragment : Fragment() {
    var item = 0
    lateinit var binding: FragmentNewsCenterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = arguments?.getInt("extra_news")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsCenterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadNews(item)
    }

    private fun loadNews(id: Int) {
        ApiService.apiClient().getNewsById(id)
            .enqueue(object : Callback<BaseResponse<List<NewsModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<NewsModel>>>,
                    response: Response<BaseResponse<List<NewsModel>>>
                ) {
                    if (response.body()!!.success) {
                        binding.recyclerNews.layoutManager =
                            LinearLayoutManager(requireActivity())
                        binding.recyclerNews.adapter =
                            NewsAdapter(response.body()?.data ?: emptyList())
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<List<NewsModel>>>, t: Throwable) {
                    Toast.makeText(requireActivity(), t.localizedMessage, Toast.LENGTH_SHORT).show()
                }

            })
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            NewsCenterFragment()
    }
}