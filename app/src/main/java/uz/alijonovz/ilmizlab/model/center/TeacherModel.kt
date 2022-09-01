package uz.alijonovz.ilmizlab.model.center

data class TeacherModel(
    val id: Int,
    val center_id: Int,
    val name: String,
    val info_link: String,
    val specialization: String,
    val experience: String,
    val avatar: String
)
