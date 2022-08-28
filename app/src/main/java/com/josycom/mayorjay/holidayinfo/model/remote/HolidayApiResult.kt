package com.josycom.mayorjay.holidayinfo.model.remote

sealed class HolidayApiResult {

    object Loading: HolidayApiResult()
    data class Success(val data: List<Any>): HolidayApiResult()
    data class Error(val error: Throwable): HolidayApiResult()
}