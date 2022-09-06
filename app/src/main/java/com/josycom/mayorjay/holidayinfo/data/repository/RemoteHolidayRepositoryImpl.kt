package com.josycom.mayorjay.holidayinfo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.josycom.mayorjay.holidayinfo.data.local.datasource.LocalDataSource
import com.josycom.mayorjay.holidayinfo.data.mapper.toCountry
import com.josycom.mayorjay.holidayinfo.data.mapper.toCountryEntity
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.remote.result.HolidayApiResult
import com.josycom.mayorjay.holidayinfo.data.remote.datasource.RemoteDataSource
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import javax.inject.Inject

class RemoteHolidayRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource) : HolidayRepository {

    override fun getCountriesLocal(): LiveData<List<Country>> {
        return localDataSource.getCountries()
            .map { countries -> countries.map { country -> country.toCountry() } }
    }

    override suspend fun getCountriesRemote() {
        val countries = remoteDataSource.getCountries()
        localDataSource.saveCountries(countries.map { it.toCountryEntity() })
    }

    override suspend fun getHolidaysRemote(holidayRequest: HolidayRequest) {
        remoteDataSource.getHolidays(holidayRequest)
    }

    override fun getApiResult(): LiveData<HolidayApiResult> {
        return remoteDataSource.getApiResult()
    }
}