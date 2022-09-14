package com.josycom.mayorjay.holidayinfo.data.remote.service

import com.josycom.mayorjay.holidayinfo.data.remote.models.CountryResponse
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayResponse
import com.josycom.mayorjay.holidayinfo.util.Constants
import retrofit2.http.Body
import retrofit2.http.POST

interface HolidayApiService {
    @POST(Constants.COUNTRY_ENDPOINT)
    suspend fun getCountries(): CountryResponse

    @POST(Constants.HOLIDAY_ENDPOINT)
    suspend fun getHolidays(@Body holidayRequest: HolidayRequest): HolidayResponse
}