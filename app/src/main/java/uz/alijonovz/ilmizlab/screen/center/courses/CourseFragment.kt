package uz.alijonovz.ilmizlab.screen.center.courses

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
import uz.alijonovz.ilmizlab.adapter.center.CourseAdapter
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.FragmentCourseBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.center.CourseModel
import uz.alijonovz.ilmizlab.screen.MainViewModel

class CourseFragment : Fragment() {
    lateinit var binding: FragmentCourseBinding
    lateinit var viewModel: MainViewModel
    var item: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
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
        viewModel.error.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.courseData.observe(requireActivity()) {
            binding.recyclerCourse.layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerCourse.adapter = CourseAdapter(it)
        }
        viewModel.loadCourses(item)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CourseFragment()
    }
}