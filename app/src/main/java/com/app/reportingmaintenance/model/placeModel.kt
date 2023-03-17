package com.app.reportingmaintenance.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class PlaceModel(

    var name: String? = "",
    var faculty_name: String? = "",

){

    @Exclude
    fun toMap(): Map<String, Any?> {

        return mapOf(

            "name" to name,

            "faculty_name" to faculty_name,
        )
    }
}