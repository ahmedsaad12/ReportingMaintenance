package com.app.reportingmaintenance.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class PlaceModel(

    var id: String? = "",
    var name: String? = "",
    var faculty_id: String? = "",


){

    @Exclude
    fun toMap(): Map<String, Any?> {

        return mapOf(

            "id" to id,
            "name" to name,

            "faculty_id" to faculty_id,
        )
    }
}