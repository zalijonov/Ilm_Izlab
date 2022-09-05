package uz.alijonovz.ilmizlab.screen.category

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import org.greenrobot.eventbus.EventBus
import uz.alijonovz.ilmizlab.adapter.category.listactivity.CategoryListAdapter
import uz.alijonovz.ilmizlab.adapter.category.listactivity.CategoryListAdapterListener
import uz.alijonovz.ilmizlab.databinding.ActivityCategoryListBinding
import uz.alijonovz.ilmizlab.model.category.CategoryIdModel
import uz.alijonovz.ilmizlab.screen.BaseActivity
import uz.alijonovz.ilmizlab.screen.MainViewModel
import uz.alijonovz.ilmizlab.utils.PrefUtils

class CategoryListActivity : BaseActivity<ActivityCategoryListBinding>() {
    lateinit var viewModel: MainViewModel
    var categoryId = PrefUtils.getCategoryId()
    var scienceId = PrefUtils.getScienceId()
    var categoryName = ""

    override fun getViewBinding(): ActivityCategoryListBinding {
        return ActivityCategoryListBinding.inflate(layoutInflater)
    }

    override fun initView() {
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

    override fun loadData() {
        viewModel.loadCategory()
    }

    override fun updateData() {

    }

}