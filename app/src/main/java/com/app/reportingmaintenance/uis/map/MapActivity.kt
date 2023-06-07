package com.app.reportingmaintenance.uis.map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity

import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.databinding.ActivityMapBinding
import com.app.reportingmaintenance.share.Common
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.model.PlaceGeocodeData
import com.app.reportingmaintenance.model.PlaceMapDetailsData
import com.app.reportingmaintenance.model.SelectedLocation
import com.app.reportingmaintenance.remote.Api
import android.content.Intent
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class MapActivity: AppCompatActivity() , OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,Api {
    var binding: ActivityMapBinding? = null
    var lang: String? = null
    var lat = 0.0
    var lng = 0.0
    var address = ""
    var mMap: GoogleMap? = null
    var marker: Marker? = null
    val zoom = 15.0f
    var googleApiClient: GoogleApiClient? = null
    var locationRequest: LocationRequest? = null
    var locationCallback: LocationCallback? = null
    val fineLocPerm = Manifest.permission.ACCESS_FINE_LOCATION
    val loc_req = 1225
    var from = ""

    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
//        getDataFromIntent()
        initView()
    }

//    open fun getDataFromIntent() {
//        val intent: Intent = getIntent()
//        if (intent.hasExtra("from")) {
//            from = intent.getStringExtra("from")!!
//        }
//    }

    open fun initView() {

        binding!!.lang = "en"
        binding!!.progBar.indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(this, R.color.colorPrimary),
            PorterDuff.Mode.SRC_IN
        )
        binding!!.edtSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId === EditorInfo.IME_ACTION_SEARCH) {
                val query: String = binding!!.edtSearch.text.toString()
                if (!TextUtils.isEmpty(query)) {
                    Common.CloseKeyBoard(this@MapActivity, binding!!.edtSearch)
                    Search(query)
                    return@setOnEditorActionListener false
                }
            }
            false
        }
        binding!!.btnSelect.setOnClickListener { view ->
            address = binding!!.edtSearch.text.toString()
            if (address.isNotEmpty()) {
                val selectedLocation = SelectedLocation()
                selectedLocation.SelectedLocation(lat, lng, address)
                val intent: Intent
                Log.e("ldldld",selectedLocation.getAddress()+" "+selectedLocation.getLat()+" "+selectedLocation.getLng())
                intent= getIntent()
                intent?.putExtra("location", selectedLocation)!!
                setResult(RESULT_OK, intent)
                finish()
            } else {
                Toast.makeText(this@MapActivity, "choose Address", Toast.LENGTH_SHORT).show()
            }
        }
        updateUI()
        CheckPermission()
    }

    open fun CheckPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                fineLocPerm
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(fineLocPerm), loc_req)
        } else {
            initGoogleApi()
        }
    }

    open fun initGoogleApi() {
        googleApiClient = GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()
        googleApiClient!!.connect()
    }

    open fun updateUI() {
        val fragment = supportFragmentManager.findFragmentById(R.id.map1) as SupportMapFragment
        fragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            mMap = googleMap
            mMap!!.isTrafficEnabled = false
            mMap!!.isBuildingsEnabled = false
            mMap!!.isIndoorEnabled = true
            mMap!!.setOnMapClickListener { latLng: LatLng ->
                lat = latLng.latitude
                lng = latLng.longitude
                AddMarker(lat, lng)
                getGeoData(lat, lng)
            }
        }
    }

    open fun Search(query: String) {
        binding!!.progBar.visibility = View.VISIBLE
        val fields = "id,place_id,name,geometry,formatted_address"
        getService("https://maps.googleapis.com/maps/api/")
            ?.searchOnMap("textquery", query, fields, lang, "AIzaSyBGJZRVH7V8iTcIrjCjJaXwpNWbeIKDiRk")
            ?.enqueue(object : Callback<PlaceMapDetailsData?> {
                override fun onResponse(
                    call: Call<PlaceMapDetailsData?>,
                    response: Response<PlaceMapDetailsData?>
                ) {
                    binding!!.progBar.visibility = View.GONE
                    if (response.isSuccessful && response.body() != null) {
                        if (response.body()!!.getCandidates()!!.size > 0) {
                            address = (response.body()!!.getCandidates()!!.get(0)!!.formatted_address
                                ?.replace("Unnamed Road,", "") ?: binding!!.edtSearch.setText(address + "" )).toString()
                            AddMarker(
                                response.body()!!.getCandidates()!!.get(0)!!.geometry!!.location!!
                                    .lat,
                                response.body()!!.getCandidates()!!.get(0)!!.geometry!!.location!!
                                    .lng
                            )
                        }
                    } else {
                        binding!!.progBar.visibility = View.GONE
                        try {
                            Log.e("error_code", response.errorBody()!!.string())
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<PlaceMapDetailsData?>, t: Throwable) {
                    try {
                        binding!!.progBar.visibility = View.GONE

                        //  Toast.makeText(MapActivity.this, getString(R.string.something), Toast.LENGTH_LONG).show();
                    } catch (e: Exception) {
                    }
                }
            })
    }

    open fun getGeoData(lat: Double, lng: Double) {
        binding!!.progBar.visibility = View.VISIBLE
        val location = "$lat,$lng"
        getService("https://maps.googleapis.com/maps/api/")
            ?.getGeoData(location, lang, "AIzaSyBGJZRVH7V8iTcIrjCjJaXwpNWbeIKDiRk")
            ?.enqueue(object : Callback<PlaceGeocodeData?> {
                override fun onResponse(
                    call: Call<PlaceGeocodeData?>,
                    response: Response<PlaceGeocodeData?>
                ) {
                    binding!!.progBar.visibility = View.GONE
                    if (response.isSuccessful && response.body() != null) {
                     //   Log.e("response", (response.body()!!.getResults()!!.get(0)!!.formatted_address!!))
                        if (response.body()!!.getResults()!!.size > 0) {
                            binding!!.btnSelect.visibility = View.VISIBLE
                            address = (response.body()!!.getResults()!!.get(0)!!.formatted_address
                                ?.replace("Unnamed Road,", "") ?: binding!!.edtSearch.setText(address + "" )).toString()
                            binding!!.edtSearch.setText(address + "" )
                        }
                    } else {
                        try {
                            Log.e("error_code", response.errorBody()!!.string())
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onFailure(call: Call<PlaceGeocodeData?>, t: Throwable) {
                    try {
                        binding!!.progBar.visibility = View.GONE

                        //   Toast.makeText(MapActivity.this, getString(R.string.something), Toast.LENGTH_LONG).show();
                    } catch (e: Exception) {
                    }
                }
            })
    }

    open fun AddMarker(lat: Double, lng: Double) {
        this.lat = lat
        this.lng = lng
        if (marker == null) {
            marker = mMap!!.addMarker(
                MarkerOptions().position(LatLng(lat, lng))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), zoom))
        } else {
            marker!!.position = LatLng(lat, lng)
            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lng), zoom))
        }
    }


    override fun onConnected(bundle: Bundle?) {
        initLocationRequest()
    }

    open fun initLocationRequest() {
        locationRequest = LocationRequest.create()
        locationRequest!!.fastestInterval = 1000
        locationRequest!!.interval = 60000
        val request = LocationSettingsRequest.Builder()
        request.addLocationRequest(locationRequest!!)
        request.setAlwaysShow(false)
        val result = LocationServices.SettingsApi.checkLocationSettings(
            googleApiClient!!, request.build()
        )
        result.setResultCallback { locationSettingsResult ->
            val status = locationSettingsResult.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> startLocationUpdate()
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> try {
                    status.startResolutionForResult(this@MapActivity, 100)
                } catch (e: SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onConnectionSuspended(i: Int) {
        if (googleApiClient != null) {
            googleApiClient!!.connect()
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {}


    @SuppressLint("MissingPermission")
    open fun startLocationUpdate() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                onLocationChanged(locationResult.lastLocation)
            }
        }
        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(locationRequest!!, locationCallback!!, Looper.myLooper()!!)
    }

    override fun onLocationChanged(location: Location) {
        lat = location.latitude
        lng = location.longitude
        AddMarker(lat, lng)
        getGeoData(lat, lng)
        if (googleApiClient != null) {
            LocationServices.getFusedLocationProviderClient(this)
                .removeLocationUpdates(locationCallback!!)
            googleApiClient!!.disconnect()
            googleApiClient = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (googleApiClient != null) {
            if (locationCallback != null) {
                LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(
                    locationCallback!!
                )
                googleApiClient!!.disconnect()
                googleApiClient = null
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == loc_req) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initGoogleApi()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            startLocationUpdate()
        }
    }


    override fun onBackPressed() {
        if (from != null && !from.isEmpty()) {
            super.onBackPressed()
        }
    }



}
