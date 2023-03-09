package com.app.reportingmaintenance.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class UserModel(
    var studentNumber: String? = "",
    var email: String? = "",
    var password: String? = "",
    var name: String? = "",
    var phone: String? = "",
    var user_type: String? = "",
){

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "email" to email,
            "password" to password,
            "studentNumber" to studentNumber,
            "name" to name,
            "phone" to phone,
            "user_type" to user_type,
        )
    }
}