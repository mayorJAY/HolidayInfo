package com.josycom.mayorjay.holidayinfo.model

import androidx.lifecycle.LiveData
import com.josycom.mayorjay.holidayinfo.model.remote.HolidayApiResult
import com.josycom.mayorjay.holidayinfo.model.remote.models.HolidayRequest

interface HolidayRepository {

    fun getCountries()
    fun getHolidays(holidayRequest: HolidayRequest)
    fun getApiResult(): LiveData<HolidayApiResult>
}