package com.josycom.mayorjay.holidayinfo.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HolidayRemote(val date: String,
                         val name: String,
                         val localName: String,
                         val countryCode: String,
                         @Json(name = "counties") val regions: List<String>?,
                         val types: List<String>?)