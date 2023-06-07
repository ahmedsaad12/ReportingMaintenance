package com.app.reportingmaintenance.model

import java.io.Serializable

class SelectedLocation(): Serializable {
    private var lat = 0.0
    private var lng = 0.0
    private var address: String? = null

    fun SelectedLocation(lat: Double, lng: Double, address: String?) {
        this.lat = lat
        this.lng = lng
        this.address = address
    }

    fun getLat(): Double {
        return lat
    }

    fun getLng(): Double {
        return lng
    }

    fun getAddress(): String? {
        return address
    }
}