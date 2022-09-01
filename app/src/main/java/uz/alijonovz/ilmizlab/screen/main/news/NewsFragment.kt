package uz.alijonovz.ilmizlab.screen.main.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alijonovz.ilmizlab.adapter.center.NewsAdapter
import uz.alijonovz.ilmizlab.databinding.FragmentNewsBinding
import uz.alijonovz.ilmizlab.screen.main.MainViewModel

class NewsFragment : Fragment() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.newsData.observe(requireActivity()) {
            binding.recyclerNews.layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerNews.adapter = NewsAdapter(it)
        }

        viewModel.progress.observe(requireActivity()){
            binding.swipe.isRefreshing = it
        }

        binding.swipe.setOnRefreshListener {
            loadData()
        }
        loadData()
    }

    private fun loadData() {
        viewModel.loadNews()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NewsFragment()
    }
}