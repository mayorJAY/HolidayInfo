package com.josycom.mayorjay.holidayinfo.data.repository

import androidx.lifecycle.LiveData
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.remote.result.HolidayApiResult
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest

interface HolidayRepository {

    suspend fun getCountriesRemote()
    fun getCountriesLocal(): LiveData<List<Country>>
    suspend fun getHolidaysRemote(holidayRequest: HolidayRequest)
    fun getApiResult(): LiveData<HolidayApiResult>
}