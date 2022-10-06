package com.josycom.mayorjay.holidayinfo.data.repository

import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.model.Holiday
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest

interface HolidayInfoRepository {

    suspend fun getCountries(): Result<List<Country>>
    suspend fun getHolidays(holidayRequest: HolidayRequest): Result<List<Holiday>>
}