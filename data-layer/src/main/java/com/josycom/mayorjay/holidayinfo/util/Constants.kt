package com.josycom.mayorjay.holidayinfo.util

object Constants {

    const val BASE_URL = "https://date.nager.at"
    const val COUNTRY_ENDPOINT = "/api/v3/AvailableCountries"
    const val HOLIDAY_ENDPOINT = "/api/v3/PublicHolidays/{year}/{countryCode}"
    const val COUNTRY_KEY = "countryExtraKey"
    const val YEAR_KEY = "yearExtraKey"
    const val DURATION = 300000L
}