package uz.alijonovz.ilmizlab.api

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import uz.alijonovz.ilmizlab.model.BaseResponse
import uz.alijonovz.ilmizlab.model.OfferModel
import uz.alijonovz.ilmizlab.model.category.CategoryModel
import uz.alijonovz.ilmizlab.model.center.*
import uz.alijonovz.ilmizlab.model.center.request.MakeRatingModel
import uz.alijonovz.ilmizlab.model.center.request.Subscribe
import uz.alijonovz.ilmizlab.model.login.*
import uz.alijonovz.ilmizlab.model.region.RegionModel
import uz.alijonovz.ilmizlab.model.request.GetCenterByIdRequest

interface Api {

    @POST("make_rating")
    fun makeRating(@Body request: MakeRatingModel): Observable<BaseResponse<String>>

    @GET("categories")
    fun getCategory(): Observable<BaseResponse<List<CategoryModel>>>

    @GET("offers")
    fun getOffers(): Observable<BaseResponse<List<OfferModel>>>

    @GET("get_courses/{id}")
    fun getCourse(@Path("id") id: Int): Observable<BaseResponse<List<CourseModel>>>

    @GET("get_news/{id}")
    fun getNewsById(@Path("id") id: Int): Observable<BaseResponse<List<NewsModel>>>

    @GET("get_ratings/{id}")
    fun getRatings(@Path("id") id: Int): Observable<BaseResponse<List<RatingModel>>>

    @GET("get_news")
    fun getNews(): Observable<BaseResponse<List<NewsModel>>>

    @GET("course_teachers/{id}")
    fun getTeachers(@Path("id") id: Int): Observable<BaseResponse<List<TeacherModel>>>

    @POST("training_centers")
    fun getCentersByFilter(@Body request: GetCenterByIdRequest): Observable<BaseResponse<List<CenterModel>>>

    @POST("set_subscriber")
    fun setSubscriber(@Body request: Subscribe): Call<BaseResponse<String>>

    @GET("regions")
    fun getRegions(): Observable<BaseResponse<List<RegionModel>>>

    @GET("news/{id}/content")
    fun getNewsContent(@Path("id") id: Int): Observable<BaseResponse<NewsModel>>

    // ------Authorization-------//

    @POST("check_phone")
    fun checkPhone(@Body request: CheckPhoneRequest): Observable<BaseResponse<CheckResult>>

    @POST("send_confirm_code")
    fun sendConfirmCode(@Body sms_code: ConfirmRequest): Observable<BaseResponse<CheckResult>>

    @POST("login")
    fun login(@Body request: LoginModel): Observable<BaseResponse<GetTokenModel>>

    @POST("registration")
    fun registration(@Body request: RegistrationRequest): Observable<BaseResponse<GetTokenModel>>

    @POST("reset_password")
    fun confirm(@Body request: ConfirmUser): Observable<BaseResponse<GetTokenModel>>

    @GET("user")
    fun getUser(): Observable<BaseResponse<GetTokenModel>>

    @GET("check_subscriber/{id}")
    fun checkSubscriber(@Path("id") id: Int): Observable<BaseResponse<Boolean>>
}