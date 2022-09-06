package uz.alijonovz.ilmizlab.screen

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding> : Fragment() {

    abstract fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): T

    abstract fun initView()
    abstract fun loadData()
    abstract fun loadUpdate()
    lateinit var binding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = getViewBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        loadData()
        loadUpdate()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}