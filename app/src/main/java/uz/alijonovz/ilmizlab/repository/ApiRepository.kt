package uz.alijonovz.ilmizlab.repository

import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import uz.alijonovz.ilmizlab.api.ApiService
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.OfferModel
import uz.alijonovz.ilmizlab.model.category.CategoryModel
import uz.alijonovz.ilmizlab.model.center.*
import uz.alijonovz.ilmizlab.model.center.request.MakeRatingModel
import uz.alijonovz.ilmizlab.model.login.*
import uz.alijonovz.ilmizlab.model.region.RegionModel
import uz.alijonovz.ilmizlab.model.request.GetCenterByIdRequest
import uz.alijonovz.ilmizlab.screen.main.MainActivity
import uz.alijonovz.ilmizlab.utils.PrefUtils

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

    fun makeRating(
        rating: Double,
        comment: String,
        center_id: Int,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<String>
    ) {
        progress.value = true
        ApiService.apiClient().makeRating(MakeRatingModel(rating, comment, center_id))
            .enqueue(object : Callback<BaseResponse<Any>> {
                override fun onResponse(
                    call: Call<BaseResponse<Any>>,
                    response: Response<BaseResponse<Any>>
                ) {
                    progress.value = false
                    if (response.isSuccessful) {
                        success.value = response.body()!!.message
                    } else {
                        error.value = response.message()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<Any>>, t: Throwable) {
                    progress.value = false
                    error.value = t.localizedMessage
                }

            })
    }

    fun loadRegion(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<RegionModel>>
    ) {
        progress.value = true
        ApiService.apiClient().getRegions()
            .enqueue(object : Callback<BaseResponse<List<RegionModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<RegionModel>>>,
                    response: Response<BaseResponse<List<RegionModel>>>
                ) {
                    progress.value = false
                    if (response.body()!!.success) {
                        success.value = response.body()?.data ?: emptyList()
                    } else
                        error.value = response.body()!!.message
                }

                override fun onFailure(call: Call<BaseResponse<List<RegionModel>>>, t: Throwable) {
                    progress.value = false
                    error.value = t.localizedMessage
                }
            })
    }

    fun loadNewsContent(
        id: Int,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<NewsModel>
    ) {
        ApiService.apiClient().getNewsContent(id)
            .enqueue(object : Callback<BaseResponse<NewsModel>> {
                override fun onResponse(
                    call: Call<BaseResponse<NewsModel>>,
                    response: Response<BaseResponse<NewsModel>>
                ) {
                    if (response.body()!!.success) {
                        success.value = response.body()!!.data
                    } else {
                        error.value = response.body()!!.message
                    }
                }

                override fun onFailure(call: Call<BaseResponse<NewsModel>>, t: Throwable) {
                    error.value = t.localizedMessage
                }

            })
    }

    fun getUser(error: MutableLiveData<String>, success: MutableLiveData<GetTokenModel>) {
        ApiService.apiClient().getUser().enqueue(object : Callback<BaseResponse<GetTokenModel>> {
            override fun onResponse(
                call: Call<BaseResponse<GetTokenModel>>,
                response: Response<BaseResponse<GetTokenModel>>
            ) {
                if (response.body()!!.success) {
                    success.value = response.body()!!.data
                }
            }

            override fun onFailure(call: Call<BaseResponse<GetTokenModel>>, t: Throwable) {
                error.value = t.localizedMessage
            }
        })
    }

    fun loadTeachers(
        id: Int,
        error: MutableLiveData<String>,
        success: MutableLiveData<List<TeacherModel>>
    ) {
        ApiService.apiClient().getTeachers(id)
            .enqueue(object : Callback<BaseResponse<List<TeacherModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<TeacherModel>>>,
                    response: Response<BaseResponse<List<TeacherModel>>>
                ) {
                    if (response.body()!!.success) {
                        success.value = response.body()?.data ?: emptyList()
                    } else
                        error.value = response.body()!!.message
                }

                override fun onFailure(call: Call<BaseResponse<List<TeacherModel>>>, t: Throwable) {
                    error.value = t.localizedMessage
                }

            })
    }

    fun loadComments(
        id: Int,
        error: MutableLiveData<String>,
        success: MutableLiveData<List<RatingModel>>
    ) {
        ApiService.apiClient().getRatings(id)
            .enqueue(object : Callback<BaseResponse<List<RatingModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<RatingModel>>>,
                    response: Response<BaseResponse<List<RatingModel>>>
                ) {
                    if (response.body()!!.success) {
                        success.value = response.body()?.data ?: emptyList()
                    } else {
                        error.value = response.body()!!.message
                    }
                }

                override fun onFailure(call: Call<BaseResponse<List<RatingModel>>>, t: Throwable) {
                    error.value = t.localizedMessage
                }
            })
    }

    fun loadNews(
        id: Int,
        error: MutableLiveData<String>,
        success: MutableLiveData<List<NewsModel>>
    ) {
        ApiService.apiClient().getNewsById(id)
            .enqueue(object : Callback<BaseResponse<List<NewsModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<NewsModel>>>,
                    response: Response<BaseResponse<List<NewsModel>>>
                ) {
                    if (response.body()!!.success) {
                        success.value = response.body()?.data ?: emptyList()
                    } else {
                        error.value = response.body()!!.message
                    }
                }

                override fun onFailure(call: Call<BaseResponse<List<NewsModel>>>, t: Throwable) {
                    error.value = t.localizedMessage
                }

            })
    }

    fun loadCourses(
        id: Int,
        error: MutableLiveData<String>,
        success: MutableLiveData<List<CourseModel>>
    ) {
        ApiService.apiClient().getCourse(id)
            .enqueue(object : Callback<BaseResponse<List<CourseModel>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<CourseModel>>>,
                    response: Response<BaseResponse<List<CourseModel>>>
                ) {
                    if (response.body()!!.success) {
                        success.value = response.body()?.data ?: emptyList()
                    } else {
                        error.value = response.body()!!.message
                    }
                }

                override fun onFailure(call: Call<BaseResponse<List<CourseModel>>>, t: Throwable) {
                    error.value = t.localizedMessage
                }

            })
    }

    fun checkPhone(
        phone: String,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<CheckResult>
    ) {
        progress.value = true
        ApiService.apiClient().checkPhone(CheckPhoneRequest(phone))
            .enqueue(object : Callback<BaseResponse<CheckResult>> {
                override fun onResponse(
                    call: Call<BaseResponse<CheckResult>>,
                    response: Response<BaseResponse<CheckResult>>
                ) {
                    progress.value = false
                    if (response.isSuccessful) {
                        if (response.body()!!.success) {
                            success.value = response.body()!!.data
                        } else {
                            error.value = response.body()!!.message
                        }
                    } else {
                        error.value = response.message()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<CheckResult>>, t: Throwable) {

                    progress.value = false
                    error.value = t.localizedMessage
                }

            })
    }

    fun login(
        phone: String,
        password: String,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<GetTokenModel>
    ) {
        progress.value = true
        ApiService.apiClient().login(LoginModel(phone, password))
            .enqueue(object : Callback<BaseResponse<GetTokenModel>> {
                override fun onResponse(

                    call: Call<BaseResponse<GetTokenModel>>,
                    response: Response<BaseResponse<GetTokenModel>>
                ) {
                    progress.value = false
                    if (response.body()!!.success) {
                        success.value = response.body()!!.data
                    } else {
                        error.value = response.body()!!.message
                    }
                }

                override fun onFailure(call: Call<BaseResponse<GetTokenModel>>, t: Throwable) {
                    progress.value = false
                    error.value = t.localizedMessage
                }

            })
    }

    fun sendCode(
        smsCode: String,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<CheckResult>
    ) {
        progress.value = true
        ApiService.apiClient().sendConfirmCode(ConfirmRequest(smsCode))
            .enqueue(object : Callback<BaseResponse<CheckResult>> {
                override fun onResponse(
                    call: Call<BaseResponse<CheckResult>>,
                    response: Response<BaseResponse<CheckResult>>
                ) {
                    progress.value = false
                    if (response.body()!!.success) {
                        success.value = response.body()!!.data
                    } else {
                        error.value = response.body()!!.message
                    }
                }

                override fun onFailure(call: Call<BaseResponse<CheckResult>>, t: Throwable) {
                    progress.value = false
                    error.value = t.localizedMessage
                }

            })
    }

    fun confirmUser(
        phone: String,
        password: String,
        sms_code: String,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<GetTokenModel>
    ) {
        progress.value = true
        ApiService.apiClient().confirm(ConfirmUser(phone, password, sms_code))
            .enqueue(object : Callback<BaseResponse<GetTokenModel>> {
                override fun onResponse(
                    call: Call<BaseResponse<GetTokenModel>>,
                    response: Response<BaseResponse<GetTokenModel>>
                ) {
                    progress.value = false
                    if (response.body()!!.success) {
                        success.value = response.body()!!.data
                    } else {
                        error.value = response.body()!!.message
                    }
                }

                override fun onFailure(call: Call<BaseResponse<GetTokenModel>>, t: Throwable) {
                    error.value = t.localizedMessage
                }

            })
    }
}