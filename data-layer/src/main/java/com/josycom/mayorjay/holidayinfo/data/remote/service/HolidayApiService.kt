package com.josycom.mayorjay.holidayinfo.data.remote.service

import com.josycom.mayorjay.holidayinfo.data.remote.models.CountryRemote
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRemote
import com.josycom.mayorjay.holidayinfo.util.Constants
import retrofit2.http.GET
import retrofit2.http.Path

interface HolidayApiService {

    @GET(Constants.COUNTRY_ENDPOINT)
    suspend fun getCountries(): List<CountryRemote>

    @GET(Constants.HOLIDAY_ENDPOINT)
    suspend fun getHolidays(@Path("year") year: Int,
                            @Path("countryCode") code: String): List<HolidayRemote>
}