package uz.alijonovz.ilmizlab.repository

import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.OfferModel
import uz.alijonovz.ilmizlab.model.category.CategoryModel
import uz.alijonovz.ilmizlab.model.center.CenterModel
import uz.alijonovz.ilmizlab.model.center.NewsModel
import uz.alijonovz.ilmizlab.model.request.GetCenterByIdRequest

class ApiRepository {
    fun loadCategory(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<CategoryModel>>
    ) {
        progress.value = true
        ApiService.apiClient().getCategory()
            .enqueue(object : Callback<BaseResponse<List<CategoryModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<CategoryModel>>>,
                    response: Response<BaseResponse<List<CategoryModel>>>
                ) {
                    progress.value = false
                    if (response.body()!!.success) {
                        success.value = response.body()?.data ?: emptyList()
                    } else {
                        error.value = response.body()!!.message
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<List<CategoryModel>>>,
                    t: Throwable
                ) {
                    progress.value = false
                    error.value = t.localizedMessage
                }
            })
    }

    fun loadCenters(
        getCenterByIdRequest: GetCenterByIdRequest,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<CenterModel>>
    ) {
        progress.value = true
        ApiService.apiClient().getCentersByFilter(getCenterByIdRequest)
            .enqueue(object : Callback<BaseResponse<List<CenterModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<CenterModel>>>,
                    response: Response<BaseResponse<List<CenterModel>>>
                ) {
                    progress.value = false
                    if (response.body()!!.success) {
                        success.value = response.body()?.data ?: emptyList()
                    } else
                        error.value = response.body()!!.message
                }

                override fun onFailure(call: Call<BaseResponse<List<CenterModel>>>, t: Throwable) {
                    progress.value = false
                    error.value = t.localizedMessage
                }

            })
    }

    fun loadOffers(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<OfferModel>>
    ) {
        progress.value = true
        ApiService.apiClient().getOffers()
            .enqueue(object : Callback<BaseResponse<List<OfferModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<OfferModel>>>,
                    response: Response<BaseResponse<List<OfferModel>>>
                ) {
                    progress.value = false
                    if (response.body()!!.success) {
                        success.value = response.body()?.data ?: emptyList()
                    } else
                        error.value = response.body()!!.message
                }

                override fun onFailure(call: Call<BaseResponse<List<OfferModel>>>, t: Throwable) {
                    progress.value = false
                    error.value = t.localizedMessage
                }

            })
    }

    fun loadNews(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<NewsModel>>
    ) {
        progress.value = true
        ApiService.apiClient().getNews().enqueue(object : Callback<BaseResponse<List<NewsModel>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<NewsModel>>>,
                response: Response<BaseResponse<List<NewsModel>>>
            ) {
                progress.value = false
                if (response.body()!!.success) {
                    success.value = response.body()?.data ?: emptyList()
                } else
                    error.value = response.body()!!.message
            }

            override fun onFailure(call: Call<BaseResponse<List<NewsModel>>>, t: Throwable) {
                progress.value = false
                error.value = t.localizedMessage
            }
        })
    }
}