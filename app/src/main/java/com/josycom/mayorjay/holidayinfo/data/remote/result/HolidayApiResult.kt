package com.josycom.mayorjay.holidayinfo.data.remote.result

import com.josycom.mayorjay.holidayinfo.data.model.Holiday

sealed class HolidayApiResult {

    object Loading: HolidayApiResult()
    data class Success(val data: List<Holiday>): HolidayApiResult()
    data class Error(val error: Throwable): HolidayApiResult()
}