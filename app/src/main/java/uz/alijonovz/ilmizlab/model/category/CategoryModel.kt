package uz.alijonovz.ilmizlab.model.category

import java.io.Serializable

data class CategoryModel(
    val created_at: String,
    val icon: String,
    val id: Int,
    val sciences: List<Science>,
    val title: String,
    val updated_at: String,
    var checked: Boolean = false
) : Serializable