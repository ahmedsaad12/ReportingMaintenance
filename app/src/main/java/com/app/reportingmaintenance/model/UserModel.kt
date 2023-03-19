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
    var id: String? = "",
){

    @Exclude
    fun toMap(): Map<String, Any?> {
        if( studentNumber!!.isNotEmpty()){

        return  mapOf(
            "email" to email,
            "password" to password,
            "studentNumber" to studentNumber,
            "name" to name,
            "phone" to phone,
            "user_type" to user_type,
        )}
        else{
        return mapOf(
            "email" to email,
            "password" to password,

            "name" to name,

            "user_type" to user_type,
            "id" to id
        )
    }}
}