package com.josycom.mayorjay.holidayinfo.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CountryRemote(@Json(name = "countryCode") val code: String,
                         val name: String)