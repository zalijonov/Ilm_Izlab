package uz.alijonovz.ilmizlab.screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.alijonovz.ilmizlab.model.OfferModel
import uz.alijonovz.ilmizlab.model.category.CategoryModel
import uz.alijonovz.ilmizlab.model.center.*
import uz.alijonovz.ilmizlab.model.login.CheckResult
import uz.alijonovz.ilmizlab.model.login.GetTokenModel
import uz.alijonovz.ilmizlab.model.region.RegionModel
import uz.alijonovz.ilmizlab.model.request.GetCenterByIdRequest
import uz.alijonovz.ilmizlab.repository.ApiRepository
import uz.alijonovz.ilmizlab.utils.Constants

class MainViewModel : ViewModel() {

    private var repository = ApiRepository()

    private var _progress = MutableLiveData<Boolean>()
    val progress: MutableLiveData<Boolean>
        get() = _progress

    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private var _offerData = MutableLiveData<List<OfferModel>>()
    val offerData: LiveData<List<OfferModel>>
        get() = _offerData

    private var _centerData = MutableLiveData<List<CenterModel>>()
    val centerData: LiveData<List<CenterModel>>
        get() = _centerData

    private var _closeCenter = MutableLiveData<List<CenterModel>>()
    val closeCenter: LiveData<List<CenterModel>>
        get() = _closeCenter

    private var _categoryData = MutableLiveData<List<CategoryModel>>()
    val categoryData: LiveData<List<CategoryModel>>
        get() = _categoryData

    private var _newsData = MutableLiveData<List<NewsModel>>()
    val newsData: LiveData<List<NewsModel>>
        get() = _newsData

    private var _ratingData = MutableLiveData<String>()
    val ratingData: LiveData<String>
        get() = _ratingData

    private var _regionData = MutableLiveData<List<RegionModel>>()
    val regionData: LiveData<List<RegionModel>>
        get() = _regionData

    private var _newsContentData = MutableLiveData<NewsModel>()
    val newsContentData: LiveData<NewsModel>
        get() = _newsContentData

    private var _tokenData = MutableLiveData<GetTokenModel>()
    val tokenData: LiveData<GetTokenModel>
        get() = _tokenData

    private var _teacherData = MutableLiveData<List<TeacherModel>>()
    val teacherData: LiveData<List<TeacherModel>>
        get() = _teacherData

    private var _commentData = MutableLiveData<List<RatingModel>>()
    val commentData: LiveData<List<RatingModel>>
        get() = _commentData

    private var _courseData = MutableLiveData<List<CourseModel>>()
    val courseData: LiveData<List<CourseModel>>
        get() = _courseData

    private var _phoneCheckData = MutableLiveData<CheckResult>()
    val phoneCheckData: LiveData<CheckResult>
        get() = _phoneCheckData

    fun loadNews() {
        repository.loadNews(_error, _progress, _newsData)
    }

    fun loadOffers() {
        repository.loadOffers(_error, _progress, _offerData)
    }

    fun loadCenters(getCenterByIdRequest: GetCenterByIdRequest) {
        repository.loadCenters(getCenterByIdRequest, _error, _progress, _centerData)
    }

    fun loadCloseCenters() {
        repository.loadCenters(
            GetCenterByIdRequest(
                sort = "distance", limit = 0, longitude = Constants.currentLongitude,
                latitude = Constants.currentLatitude
            ),
            _error,
            _progress,
            _closeCenter
        )
    }

    fun loadCategory() {
        repository.loadCategory(_error, _progress, _categoryData)
    }

    fun makeRating(rating: Double, comment: String, center_id: Int) {
        repository.makeRating(rating, comment, center_id, _error, _progress, _ratingData)
    }

    fun loadRegion() {
        repository.loadRegion(_error, _progress, _regionData)
    }

    fun loadNewsContent(id: Int) {
        repository.loadNewsContent(id, _error, _progress, _newsContentData)
    }

    fun getUser() {
        repository.getUser(_error, _tokenData)
    }

    fun loadTeacher(id: Int) {
        repository.loadTeachers(id, _error, _teacherData)
    }

    fun loadComments(id: Int) {
        repository.loadComments(id, _error, _commentData)
    }

    fun loadNews(id: Int) {
        repository.loadNews(id, _error, _newsData)
    }

    fun loadCourses(id: Int) {
        repository.loadCourses(id, _error, _courseData)
    }

    fun checkPhone(phone: String) {
        repository.checkPhone(phone, _error, _progress, _phoneCheckData)
    }

    fun login(phone: String, password: String) {
        repository.login(phone, password, _error, _progress, _tokenData)
    }

    fun sendCode(smsCode: String) {
        repository.sendCode(smsCode, _error, _progress, _phoneCheckData)
    }

    fun confirmUser(phone: String, password: String, sms_code: String) {
        repository.confirmUser(phone, password, sms_code, _error, _progress, _tokenData)
    }

    fun setSubscriber(id: Int) {
        repository.setSubscriber(id, _error, _progress)
    }
}