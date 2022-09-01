package uz.alijonovz.ilmizlab.screen.category

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.adapter.category.listactivity.CategoryListAdapter
import uz.alijonovz.ilmizlab.adapter.category.listactivity.CategoryListAdapterListener
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.databinding.ActivityCategoryListBinding
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.category.CategoryIdModel
import uz.alijonovz.ilmizlab.model.category.CategoryModel
import uz.alijonovz.ilmizlab.screen.MainViewModel
import uz.alijonovz.ilmizlab.utils.PrefUtils

class CategoryListActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryListBinding
    lateinit var viewModel: MainViewModel
    var categoryId = PrefUtils.getCategoryId()
    var scienceId = PrefUtils.getScienceId()
    var categoryName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.error.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.categoryData.observe(this) {
            binding.recyclerScience.layoutManager = LinearLayoutManager(this)
            binding.recyclerScience.adapter = CategoryListAdapter(it,
                object : CategoryListAdapterListener {
                    override fun onClick(
                        categoryId: Int,
                        scienceId: Int,
                        categoryName: String
                    ) {
                        this@CategoryListActivity.categoryId = categoryId
                        this@CategoryListActivity.scienceId = scienceId
                        this@CategoryListActivity.categoryName = categoryName
                    }
                })
        }
        viewModel.loadCategory()

        binding.btnSelect.setOnClickListener {
            val category = CategoryIdModel(
                category_id = categoryId,
                science_id = scienceId,
                category_name = categoryName
            )
            EventBus.getDefault().post(category)
            finish()
        }
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}