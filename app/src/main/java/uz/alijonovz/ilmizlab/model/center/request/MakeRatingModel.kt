package uz.alijonovz.ilmizlab.model.center.request

data class MakeRatingModel (
    val rating: Double = 5.0 ,
    val comment: String = "",
    val center_id: Int = 0
)