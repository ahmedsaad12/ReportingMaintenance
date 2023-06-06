package com.app.reportingmaintenance.service


import com.app.reportingmaintenance.model.PlaceGeocodeData
import com.app.reportingmaintenance.model.PlaceMapDetailsData
import io.reactivex.Observable

import io.reactivex.Single

import okhttp3.MultipartBody

import okhttp3.RequestBody

import retrofit2.Call

import retrofit2.Response

import retrofit2.http.Body

import retrofit2.http.Field

import retrofit2.http.FormUrlEncoded

import retrofit2.http.GET

import retrofit2.http.Header

import retrofit2.http.Multipart

import retrofit2.http.POST

import retrofit2.http.Part

import retrofit2.http.Query


abstract class Service {
    @GET("place/findplacefromtext/json")
    abstract fun searchOnMap(
        @Query(value = "inputtype") inputtype: String?,
        @Query(value = "input") input: String?,
        @Query(value = "fields") fields: String?,
        @Query(value = "language") language: String?,
        @Query(value = "key") key: String?
    ): Call<PlaceMapDetailsData?>?

    @GET("geocode/json")
    abstract fun getGeoData(
        @Query(value = "latlng") latlng: String?,
        @Query(value = "language") language: String?,
        @Query(value = "key") key: String?
    ): Call<PlaceGeocodeData?>?
}