package uz.alijonovz.ilmizlab.screen.center.courses

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
import uz.alijonovz.ilmizlab.adapter.center.CourseAdapter
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.FragmentCourseBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.center.CourseModel

class CourseFragment : Fragment() {
    lateinit var binding: FragmentCourseBinding
    var item: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = arguments?.getInt("extra_course")!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadCourses(item)
    }

    fun loadCourses(id: Int) {
        ApiService.apiClient().getCourse(id)
            .enqueue(object : Callback<BaseResponse<List<CourseModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<CourseModel>>>,
                    response: Response<BaseResponse<List<CourseModel>>>
                ) {
                    if (response.body()!!.success) {
                        binding.recyclerCourse.layoutManager =
                            LinearLayoutManager(requireActivity())
                        binding.recyclerCourse.adapter =
                            CourseAdapter(response.body()?.data ?: emptyList())
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            response.body()!!.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<List<CourseModel>>>, t: Throwable) {
                    Toast.makeText(requireActivity(), t.localizedMessage, Toast.LENGTH_SHORT).show()
                }

            })
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CourseFragment()
    }
}