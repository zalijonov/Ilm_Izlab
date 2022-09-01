package uz.alijonovz.ilmizlab.model.category

import java.io.Serializable

data class Science(
    val category_id: Int,
    val created_at: String,
    val icon: String,
    val id: Int,
    val title: String,
    var checked: Boolean = false
) : Serializable