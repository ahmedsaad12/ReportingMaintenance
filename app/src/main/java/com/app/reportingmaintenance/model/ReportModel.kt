package com.app.reportingmaintenance.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class ReportModel(
    var id: String? = "",
    var subject: String? = "",
    var desc: String? = "",
    var iddis: String? = "",
    var idfac: String? = "",
    var idplace: String? = "",
    var periority: String? = "",
    var image: String? = "",
    var status: String? = "",
    var studentid: String? = ""
) {

    @Exclude
    fun toMap(): Map<String, Any?> {
        if (image!!.isNotEmpty()) {

            return mapOf(
                "id" to id,
                "desc" to desc,
                "iddis" to iddis,
                "subject" to subject,
                "idfac" to idfac,
                "idplace" to idplace,
                "periority" to periority,
                "image" to image,
                "status" to status,
                "studentid" to studentid,
            )
        } else {
            return mapOf(
                "id" to id,
                "desc" to desc,
                "iddis" to iddis,
                "subject" to subject,
                "idfac" to idfac,
                "periority" to periority,
                "status" to status,
                "studentid" to studentid,
            )
        }
    }
}