package uz.alijonovz.ilmizlab.model.login

import java.io.Serializable

data class ConfirmUser(
    val phone: String,
    val password: String,
    val sms_code: String,
) : Serializable
