package com.josycom.mayorjay.holidayinfo.data.remote.datasource

import androidx.lifecycle.LiveData
import com.josycom.mayorjay.holidayinfo.data.remote.result.HolidayApiResult
import com.josycom.mayorjay.holidayinfo.data.remote.models.CountryRemote
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest

interface RemoteDataSource {

    suspend fun getCountries(): List<CountryRemote>
    suspend fun getHolidays(holidayRequest: HolidayRequest)
    fun getApiResult(): LiveData<HolidayApiResult>
}