package com.josycom.mayorjay.holidayinfo.model.local

data class HolidayLocal(val date: String,
                        val name: String,
                        val localName: String,
                        val countryCode: String,
                        val regions: List<String>,
                        val types: List<String>)