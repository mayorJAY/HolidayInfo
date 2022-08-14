package com.josycom.mayorjay.holidayinfo.model

import com.josycom.mayorjay.holidayinfo.model.network.models.Countries
import com.josycom.mayorjay.holidayinfo.model.network.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.model.network.models.Holidays

interface HolidayRepository {

    suspend fun getCountries(): Countries
    suspend fun getHolidays(holidayRequest: HolidayRequest): Holidays
}