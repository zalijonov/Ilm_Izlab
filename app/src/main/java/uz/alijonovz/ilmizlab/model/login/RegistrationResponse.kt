package uz.alijonovz.ilmizlab.model.login

import java.io.Serializable

data class RegistrationResponse(
var token: String,
val avatar: String,
val password:String,
val fullname: String,
val phone: String,
val status: String
): Serializable