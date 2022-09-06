package com.josycom.mayorjay.holidayinfo.data.remote

import com.josycom.mayorjay.holidayinfo.data.remote.models.CountryResponse
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayResponse
import com.josycom.mayorjay.holidayinfo.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val client = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val request = chain.request()
            .newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Bearer ${Constants.API_KEY}")
            .build()
        chain.proceed(request)
    }

private val retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .client(client.build())
    .build()

interface HolidayApiService {
    @POST(Constants.COUNTRY_ENDPOINT)
    suspend fun getCountries(): CountryResponse

    @POST(Constants.HOLIDAY_ENDPOINT)
    suspend fun getHolidays(@Body holidayRequest: HolidayRequest): HolidayResponse
}

object HolidayApi {
    val retrofitService: HolidayApiService by lazy {
        retrofit.create(HolidayApiService::class.java)
    }
}