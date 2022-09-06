package com.josycom.mayorjay.holidayinfo.data.local.datasource

import androidx.lifecycle.LiveData
import com.josycom.mayorjay.holidayinfo.data.local.entity.CountryEntity

interface LocalDataSource {

    fun getCountries(): LiveData<List<CountryEntity>>
    suspend fun saveCountries(countries: List<CountryEntity>)
}