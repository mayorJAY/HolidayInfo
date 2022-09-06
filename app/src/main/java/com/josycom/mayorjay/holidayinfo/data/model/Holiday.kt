package com.josycom.mayorjay.holidayinfo.data.model

data class Holiday(val date: String,
                   val name: String,
                   val localName: String,
                   val countryCode: String,
                   val regions: List<String>,
                   val types: List<String>)