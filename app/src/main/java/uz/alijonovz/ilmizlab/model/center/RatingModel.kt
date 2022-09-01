package uz.alijonovz.ilmizlab.model.center

data class RatingModel(
    val id: Int,
    val user_id: Int,
    val rating: Int,
    val comment: String,
    val date: String,
    val user_fullname: String,
    val user_avatar: String
)