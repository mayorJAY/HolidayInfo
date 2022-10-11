package com.josycom.mayorjay.holidayinfo.data.remote.datasource

import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.model.Holiday
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest

interface RemoteDataSource {

    suspend fun getCountries(): Result<List<Country>>
    suspend fun getHolidays(holidayRequest: HolidayRequest): Result<List<Holiday>>
}