package com.josycom.mayorjay.holidayinfo.model.network.models

import com.squareup.moshi.Json

data class Holiday(val date: String,
                   val name: String,
                   @Json(name = "local_name") val localName: String,
                   @Json(name = "country_code") val countryCode: String,
                   val regions: List<String>,
                   val types: List<String>)
