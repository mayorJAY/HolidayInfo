package com.josycom.mayorjay.holidayinfo.data.local.datasource

import com.josycom.mayorjay.holidayinfo.data.model.Country

interface LocalDataSource {

    suspend fun getCountries(): Result<List<Country>>
    suspend fun saveCountries(countries: List<Country>)
    suspend fun isEmpty(): Boolean
}