package com.app.reportingmaintenance.remote

import com.app.reportingmaintenance.service.Service
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface Api {



     fun getRetrofit(baseUrl: String): Retrofit? {
         val retrofit: Retrofit?
        val interceptor = Interceptor { chain: Interceptor.Chain ->
            val request = chain.request()
            val accept = request.newBuilder()
                .addHeader("ACCEPT", "application/json")
                .build()
            chain.proceed(accept)
        }
        val client = OkHttpClient.Builder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .addInterceptor(interceptor)
            .build()
        val gson = GsonBuilder()
            .setLenient()
            .create()
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
        return retrofit
    }


    fun  getService(baseUrl: String): Service? {
        return getRetrofit(baseUrl)!!.create(Service::class.java)
    }
}