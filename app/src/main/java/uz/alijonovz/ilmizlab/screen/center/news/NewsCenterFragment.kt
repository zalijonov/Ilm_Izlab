package uz.alijonovz.ilmizlab.screen.center.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alijonovz.ilmizlab.adapter.center.NewsAdapter
import uz.alijonovz.ilmizlab.databinding.FragmentNewsCenterBinding
import uz.alijonovz.ilmizlab.screen.BaseFragment
import uz.alijonovz.ilmizlab.screen.MainViewModel

class NewsCenterFragment : BaseFragment<FragmentNewsCenterBinding>() {
    var item = 0
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        item = arguments?.getInt("extra_news")!!
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewsCenterBinding {
        return FragmentNewsCenterBinding.inflate(inflater, container, false)
    }


    override fun initView() {
        viewModel.error.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.newsData.observe(requireActivity()) {
            binding.recyclerNews.layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerNews.adapter = NewsAdapter(it)
        }

    }

    override fun loadData() {
        viewModel.loadNews(item)
    }

    override fun loadUpdate() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = NewsCenterFragment()
    }
}