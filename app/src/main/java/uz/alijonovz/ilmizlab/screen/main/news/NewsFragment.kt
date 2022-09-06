package uz.alijonovz.ilmizlab.screen.main.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alijonovz.ilmizlab.adapter.center.NewsAdapter
import uz.alijonovz.ilmizlab.databinding.FragmentNewsBinding
import uz.alijonovz.ilmizlab.screen.BaseFragment
import uz.alijonovz.ilmizlab.screen.MainViewModel

class NewsFragment : BaseFragment<FragmentNewsBinding>() {
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentNewsBinding {
        return FragmentNewsBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        viewModel.newsData.observe(requireActivity()) {
            binding.recyclerNews.layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerNews.adapter = NewsAdapter(it)
        }

        viewModel.progress.observe(requireActivity()) {
            binding.swipe.isRefreshing = it
        }

        binding.swipe.setOnRefreshListener {
            loadData()
        }
    }

    override fun loadData() {
        viewModel.loadNews()
    }

    override fun loadUpdate() {

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NewsFragment()
    }
}