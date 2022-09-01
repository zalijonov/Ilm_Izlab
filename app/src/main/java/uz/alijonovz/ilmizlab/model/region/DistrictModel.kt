package uz.alijonovz.ilmizlab.model.region

import java.io.Serializable

data class DistrictModel(
    val id: Int,
    val region_id: Int,
    val name_uz: String,
    val district_name: String,
    var checked: Boolean = false
) : Serializable