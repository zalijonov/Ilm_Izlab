package uz.alijonovz.ilmizlab.screen.center.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import uz.alijonovz.ilmizlab.adapter.center.NewsAdapter
import uz.alijonovz.ilmizlab.databinding.FragmentNewsCenterBinding
import uz.alijonovz.ilmizlab.screen.MainViewModel

class NewsCenterFragment : Fragment() {
    var item = 0
    lateinit var binding: FragmentNewsCenterBinding
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
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
        viewModel.error.observe(requireActivity()) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.newsData.observe(requireActivity()) {
            binding.recyclerNews.layoutManager = LinearLayoutManager(requireActivity())
            binding.recyclerNews.adapter = NewsAdapter(it)
        }
        viewModel.loadNews(item)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            NewsCenterFragment()
    }
}