package uz.alijonovz.ilmizlab.model.center

import uz.alijonovz.ilmizlab.model.region.DistrictModel
import uz.alijonovz.ilmizlab.model.region.RegionModel
import java.io.Serializable

data class CenterModel(
    val id: Int,
    val region_id: Int,
    val district_id: Int,
    val name: String,
    val phone: String,
    val address: String,
    val comment: String,
    val monthly_payment_min: Int,
    val monthly_payment_max: Int,
    val main_image: String,
    val latitude: String,
    val longitude: String,
    val rating: String,
    val rating_count: Int,
    val images: MutableList<String>,
    val subsribers_count: Int,
    val courses: List<CourseModel>,
    val district: DistrictModel,
    val region: RegionModel
) : Serializable