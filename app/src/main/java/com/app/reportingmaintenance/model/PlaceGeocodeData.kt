package com.app.reportingmaintenance.model

import java.io.Serializable

class PlaceGeocodeData {
    private val results: List<Geocode?>? = null

    fun getResults(): List<Geocode?>? {
        return results
    }

    class Geocode : Serializable {
        val formatted_address: String? = null
        val place_id: String? = null
        val geometry: Geometry? = null
    }

    class Geometry : Serializable {
        val location: Location? = null
    }

    class Location : Serializable {
        val lat = 0.0
        val lng = 0.0
    }
}