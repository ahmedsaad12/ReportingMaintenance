package com.app.reportingmaintenance.model

import java.io.Serializable

class PlaceMapDetailsData {

    private val candidates: List<PlaceData?>? = null

    fun getCandidates(): List<PlaceData?>? {
        return candidates
    }

    class PlaceData : Serializable {
        val id: String? = null
        val place_id: String? = null
        val geometry: Geometry? = null
        val formatted_address: String? = null
    }

    class Geometry : Serializable {
        val location: Location? = null
    }

    class Location : Serializable {
        val lat = 0.0
        val lng = 0.0
    }
}