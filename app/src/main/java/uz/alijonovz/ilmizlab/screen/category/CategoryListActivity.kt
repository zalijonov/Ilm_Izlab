package uz.alijonovz.ilmizlab.screen.category

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

class CategoryListActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryListBinding
    var categoryId = 0
    var scienceId = 0
    var categoryName = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadCategories()

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

    fun loadCategories() {
        ApiService.apiClient().getCategory()
            .enqueue(object : Callback<BaseResponse<List<CategoryModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<CategoryModel>>>,
                    response: Response<BaseResponse<List<CategoryModel>>>
                ) {
                    binding.recyclerScience.layoutManager =
                        LinearLayoutManager(this@CategoryListActivity)
                    binding.recyclerScience.adapter = CategoryListAdapter(
                        response.body()?.data ?: emptyList(),
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

                override fun onFailure(
                    call: Call<BaseResponse<List<CategoryModel>>>,
                    t: Throwable
                ) {

                }

            })
    }
}