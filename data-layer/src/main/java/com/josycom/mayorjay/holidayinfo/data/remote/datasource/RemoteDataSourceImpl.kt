package com.josycom.mayorjay.holidayinfo.data.remote.datasource

import com.josycom.mayorjay.holidayinfo.data.mapper.toCountry
import com.josycom.mayorjay.holidayinfo.data.mapper.toHoliday
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.model.Holiday
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.data.remote.service.HolidayApiService
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: HolidayApiService): RemoteDataSource {

    override suspend fun getCountries(): List<Country> {
        return apiService.getCountries().map { countryRemote -> countryRemote.toCountry() }
    }

    override suspend fun getHolidays(holidayRequest: HolidayRequest): List<Holiday> {
        return apiService.getHolidays(holidayRequest.year.toInt(), holidayRequest.countryCode)
            .map { holidayRemote -> holidayRemote.toHoliday() }
    }
}