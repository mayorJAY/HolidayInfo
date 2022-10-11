package com.josycom.mayorjay.holidayinfo.data.remote.models

import com.squareup.moshi.Json

data class HolidayRequest(@Json(name = "country_code") val countryCode: String, val year: String = "2022")