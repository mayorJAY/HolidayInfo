package com.josycom.mayorjay.holidayinfo.model.network

import com.josycom.mayorjay.holidayinfo.model.HolidayRepository
import com.josycom.mayorjay.holidayinfo.model.network.models.Countries
import com.josycom.mayorjay.holidayinfo.model.network.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.model.network.models.Holidays

class RemoteHolidayRepositoryImpl(private val apiService: HolidayApiService) : HolidayRepository {

    override suspend fun getCountries(): Countries {
        return apiService.getCountries()
    }

    override suspend fun getHolidays(holidayRequest: HolidayRequest): Holidays {
        return apiService.getHolidays(holidayRequest)
    }
}