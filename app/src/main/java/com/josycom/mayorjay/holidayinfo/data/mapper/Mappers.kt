package com.josycom.mayorjay.holidayinfo.data.mapper

import com.josycom.mayorjay.holidayinfo.data.local.entity.CountryEntity
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.model.Holiday
import com.josycom.mayorjay.holidayinfo.data.remote.models.CountryRemote
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRemote

fun CountryRemote.toCountryEntity() = CountryEntity(
    code = this.code,
    name = this.name
)

fun CountryEntity.toCountry() = Country(
    code = this.code,
    name = this.name
)

fun HolidayRemote.toHoliday() = Holiday(
        date = this.date,
        name = this.name,
        localName = this.localName,
        countryCode = this.countryCode,
        regions = this.regions,
        types = this.types
)