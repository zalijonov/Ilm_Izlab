package uz.alijonovz.ilmizlab.model.center

import uz.alijonovz.ilmizlab.model.category.Science
import java.io.Serializable

data class CourseModel(
    val id: Int,
    val center_id: Int,
    val science_id: Int,
    val name: String,
    val monthly_payment: Int,
    val science: Science
) : Serializable