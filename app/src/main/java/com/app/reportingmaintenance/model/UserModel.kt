package com.app.reportingmaintenance.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class UserModel(
    var id: String? = "",
    var studentNumber: String? = "",
    var email: String? = "",
    var password: String? = "",
    var name: String? = "",
    var phone: String? = "",
    var user_type: String? = "",
    var disid: String? = "",
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        if (studentNumber!!.isNotEmpty()) {
            if (password!!.isNotEmpty()) {
                return mapOf(
                    "id" to id,
                    "email" to email,
                    "password" to password,
                    "studentNumber" to studentNumber,
                    "name" to name,
                    "phone" to phone,
                    "user_type" to user_type,
                )
            } else {
                return mapOf(
                    "id" to id,
                    "email" to email,
                    "studentNumber" to studentNumber,
                    "name" to name,
                    "phone" to phone,
                    "user_type" to user_type,
                )
            }
        } else {
            if(password!!.isNotEmpty()){
            return mapOf(

                "id" to id,
                "email" to email,
                "password" to password,

                "name" to name,

                "user_type" to user_type,

                "disid" to disid
            )}
            else{
                return mapOf(

                    "id" to id,
                    "email" to email,


                    "name" to name,

                    "user_type" to user_type,

                    "disid" to disid
                )
            }
        }
    }
}