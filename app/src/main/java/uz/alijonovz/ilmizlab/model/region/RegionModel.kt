package uz.alijonovz.ilmizlab.model.region

import java.io.Serializable

data class RegionModel(
    val id: Int,
    val name_uz: String,
    val region_name: String,
    val districts: List<DistrictModel>,
    var checked: Boolean = false
) : Serializable