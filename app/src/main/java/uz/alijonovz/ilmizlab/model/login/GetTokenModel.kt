package uz.alijonovz.ilmizlab.model.login

data class GetTokenModel(
    val token: String,
    val fullname: String,
    val avatar: String,
    val phone: String,
    val status: String
)