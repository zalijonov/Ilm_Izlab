package uz.alijonovz.ilmizlab.repository

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
import uz.alijonovz.ilmizlab.model.center.request.Subscribe
import uz.alijonovz.ilmizlab.model.login.*
import uz.alijonovz.ilmizlab.model.region.RegionModel
import uz.alijonovz.ilmizlab.model.request.GetCenterByIdRequest

class ApiRepository : BaseRepository() {
    fun loadCategory(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<CategoryModel>>
    ) {
        sendCall(ApiService.apiClient().getCategory(), error, success, progress)
    }

    fun loadCenters(
        getCenterByIdRequest: GetCenterByIdRequest,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<CenterModel>>
    ) {
        sendCall(
            ApiService.apiClient().getCentersByFilter(getCenterByIdRequest),
            error,
            success,
            progress
        )
    }

    fun loadOffers(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<OfferModel>>
    ) {

        sendCall(ApiService.apiClient().getOffers(), error, success, progress)

    }

    fun loadNews(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<NewsModel>>
    ) {
        sendCall(ApiService.apiClient().getNews(), error, success, progress)
    }

    fun makeRating(
        rating: Double,
        comment: String,
        center_id: Int,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<String>
    ) {
        sendCall(ApiService.apiClient().makeRating(MakeRatingModel(rating, comment, center_id)), error, success, progress)
    }

    fun loadRegion(
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<List<RegionModel>>
    ) {
        sendCall(ApiService.apiClient().getRegions(), error, success, progress)
    }

    fun loadNewsContent(
        id: Int,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<NewsModel>
    ) {
        sendCall(ApiService.apiClient().getNewsContent(id), error, success, progress)
    }

    fun getUser(error: MutableLiveData<String>, success: MutableLiveData<GetTokenModel>, progress: MutableLiveData<Boolean>) {
        sendCall(ApiService.apiClient().getUser(), error, success, progress)
    }

    fun loadTeachers(
        id: Int,
        error: MutableLiveData<String>,
        success: MutableLiveData<List<TeacherModel>>,
        progress: MutableLiveData<Boolean>
    ) {
        sendCall(ApiService.apiClient().getTeachers(id), error, success, progress)
    }

    fun loadComments(
        id: Int,
        error: MutableLiveData<String>,
        success: MutableLiveData<List<RatingModel>>,
        progress: MutableLiveData<Boolean>
    ) {
        sendCall(ApiService.apiClient().getRatings(id), error, success, progress)
    }

    fun loadCourses(
        id: Int,
        error: MutableLiveData<String>,
        success: MutableLiveData<List<CourseModel>>,
        progress: MutableLiveData<Boolean>
    ) {
        sendCall(ApiService.apiClient().getCourse(id), error, success, progress)
    }

    fun checkPhone(
        phone: String,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<CheckResult>
    ) {
        sendCall(ApiService.apiClient().checkPhone(CheckPhoneRequest(phone)), error, success, progress)
    }

    fun login(
        phone: String,
        password: String,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<GetTokenModel>
    ) {
        sendCall(ApiService.apiClient().login(LoginModel(phone, password)), error, success, progress)
    }

    fun sendCode(
        smsCode: String,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<CheckResult>
    ) {
        sendCall(ApiService.apiClient().sendConfirmCode(ConfirmRequest(smsCode)), error, success, progress)
    }

    fun confirmUser(
        phone: String,
        password: String,
        sms_code: String,
        error: MutableLiveData<String>,
        progress: MutableLiveData<Boolean>,
        success: MutableLiveData<GetTokenModel>
    ) {
        sendCall(ApiService.apiClient().confirm(ConfirmUser(phone, password, sms_code)), error, success, progress)
    }

    fun setSubscriber(id: Int, error: MutableLiveData<String>, progress: MutableLiveData<Boolean>) {
        ApiService.apiClient().setSubscriber(Subscribe(id))
            .enqueue(object : Callback<BaseResponse<String>> {
                override fun onResponse(
                    call: Call<BaseResponse<String>>,
                    response: Response<BaseResponse<String>>
                ) {
                    if (response.isSuccessful) {
                        error.value = response.body()!!.message
                    } else {
                        error.value = response.message()
                    }
                }

                override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                    error.value = t.localizedMessage
                }
            })
    }
}