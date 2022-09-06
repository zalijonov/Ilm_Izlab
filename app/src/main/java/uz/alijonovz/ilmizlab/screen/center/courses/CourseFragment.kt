package uz.alijonovz.ilmizlab.screen.center.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alijonovz.ilmizlab.adapter.center.CourseAdapter
import uz.alijonovz.ilmizlab.databinding.FragmentCourseBinding
import uz.alijonovz.ilmizlab.screen.BaseFragment
import uz.alijonovz.ilmizlab.screen.MainViewModel

class CourseFragment : BaseFragment<FragmentCourseBinding>() {
    lateinit var viewModel: MainViewModel
    var item: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        item = arguments?.getInt("extra_course")!!
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCourseBinding {
        return FragmentCourseBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        viewModel.error.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.courseData.observe(requireActivity()) {
            binding.recyclerCourse.layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerCourse.adapter = CourseAdapter(it)
        }

    }

    override fun loadData() {
        viewModel.loadCourses(item)
    }

    override fun loadUpdate() {

    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CourseFragment()
    }
}