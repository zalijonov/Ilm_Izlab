package uz.alijonovz.ilmizlab.screen.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.alijonovz.ilmizlab.model.OfferModel
import uz.alijonovz.ilmizlab.model.category.CategoryModel
import uz.alijonovz.ilmizlab.model.center.CenterModel
import uz.alijonovz.ilmizlab.model.center.NewsModel
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
}