package com.josycom.mayorjay.holidayinfo.data.repository

import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.model.Holiday
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.util.Resource
import kotlinx.coroutines.flow.Flow

interface HolidayInfoRepository {

    suspend fun getCountries(): Flow<Resource<List<Country>>>
    suspend fun getHolidays(holidayRequest: HolidayRequest): Flow<Resource<List<Holiday>>>
}