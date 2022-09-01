package uz.alijonovz.ilmizlab.model.request

data class GetCenterByIdRequest(
    var region_id: Int = 0,
    var district_id: Int = 0,
    var category_id: Int = 0,
    var science_id: Int = 0,
    var keyword: String = "",
    var sort: String = "rating",
    var limit: Int = 0,
    var latitude: Double = 40.3680081,
    var longitude: Double = 71.7810391,
    var is_subscriber: Boolean = false
)