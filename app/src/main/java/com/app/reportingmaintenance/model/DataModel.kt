package com.app.reportingmaintenance.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class DataModel(

    var id: String? = "",
    var name: String? = "",
    var address:String?="",
    var latlng:String?=""

){

    @Exclude
    fun toMap(): Map<String, Any?> {
        if(address!!.isNotEmpty()){
        return mapOf(

            "id" to id,
            "name" to name,
            "address" to address,
            "latlng" to latlng,

        )}
        else{
            return mapOf(

                "id" to id,
                "name" to name,


                )
        }
    }
}