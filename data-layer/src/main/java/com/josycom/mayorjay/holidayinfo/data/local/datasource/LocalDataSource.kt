package com.josycom.mayorjay.holidayinfo.data.local.datasource

import com.josycom.mayorjay.holidayinfo.data.model.Country
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {

    fun getCountries(): Flow<List<Country>>
    suspend fun saveCountries(countries: List<Country>)
}