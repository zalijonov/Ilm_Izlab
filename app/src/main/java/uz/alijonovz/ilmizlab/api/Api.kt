package uz.alijonovz.ilmizlab.api

import retrofit2.Call
import retrofit2.http.*
import uz.alijonovz.ilmizlab.model.center.TeacherModel
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.OfferModel
import uz.alijonovz.ilmizlab.model.category.CategoryModel
import uz.alijonovz.ilmizlab.model.center.CenterModel
import uz.alijonovz.ilmizlab.model.center.CourseModel
import uz.alijonovz.ilmizlab.model.center.NewsModel
import uz.alijonovz.ilmizlab.model.center.RatingModel
import uz.alijonovz.ilmizlab.model.center.request.MakeRatingModel
import uz.alijonovz.ilmizlab.model.center.request.Subscribe
import uz.alijonovz.ilmizlab.model.login.*
import uz.alijonovz.ilmizlab.model.region.RegionModel
import uz.alijonovz.ilmizlab.model.request.GetCenterByIdRequest

interface Api {

    @POST("make_rating")
    fun makeRating(@Body request: MakeRatingModel): Call<BaseResponse<Any>>
    @GET("categories")
    fun getCategory(): Call<BaseResponse<List<CategoryModel>>>

    @GET("offers")
    fun getOffers(): Call<BaseResponse<List<OfferModel>>>

    @GET("get_courses/{id}")
    fun getCourse(@Path("id") id: Int): Call<BaseResponse<List<CourseModel>>>

    @GET("get_news/{id}")
    fun getNewsById(@Path("id") id: Int): Call<BaseResponse<List<NewsModel>>>

    @GET("get_ratings/{id}")
    fun getRatings(@Path("id") id: Int): Call<BaseResponse<List<RatingModel>>>

    @GET("get_news")
    fun getNews(): Call<BaseResponse<List<NewsModel>>>

    @GET("course_teachers/{id}")
    fun getTeachers(@Path("id") id: Int): Call<BaseResponse<List<TeacherModel>>>

    @POST("training_centers")
    fun getCentersByFilter(@Body request: GetCenterByIdRequest): Call<BaseResponse<List<CenterModel>>>

    @POST("set_subscriber")
    fun setSubscriber(@Body request: Subscribe): Call<BaseResponse<String>>

    @GET("regions")
    fun getRegions(): Call<BaseResponse<List<RegionModel>>>

    @GET("news/{id}/content")
    fun getNewsContent(@Path("id") id: Int): Call<BaseResponse<NewsModel>>

    // ------Authorization-------//

    @POST("check_phone")
    fun checkPhone(@Body request: CheckPhoneRequest): Call<BaseResponse<CheckResult>>
    @POST("send_confirm_code")
    fun sendConfirmCode(@Body sms_code: ConfirmRequest): Call<BaseResponse<CheckResult>>

    @POST("login")
    fun login(@Body request: LoginModel): Call<BaseResponse<GetTokenModel>>
    @POST("registration")
    fun registration(@Body request: RegistrationRequest): Call<BaseResponse<GetTokenModel>>

    @POST("reset_password")
    fun confirm(@Body request: ConfirmUser): Call<BaseResponse<GetTokenModel>>

    @GET("user")
    fun getUser(): Call<BaseResponse<GetTokenModel>>

    @GET("check_subscriber/{id}")
    fun checkSubscriber(@Path("id") id: Int): Call<BaseResponse<Boolean>>
}