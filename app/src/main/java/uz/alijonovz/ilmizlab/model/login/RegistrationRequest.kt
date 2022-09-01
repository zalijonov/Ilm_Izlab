package uz.alijonovz.ilmizlab.model.login

data class RegistrationRequest(
    val fullname: String,
    val phone: String,
    val sms_code: String
)