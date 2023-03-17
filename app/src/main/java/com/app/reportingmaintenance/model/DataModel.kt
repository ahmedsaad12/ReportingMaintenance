package com.app.reportingmaintenance.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class DataModel(

    var name: String? = "",

){

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(

            "name" to name,

        )
    }
}