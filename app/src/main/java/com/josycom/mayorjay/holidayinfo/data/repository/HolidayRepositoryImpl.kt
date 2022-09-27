package com.josycom.mayorjay.holidayinfo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.josycom.mayorjay.holidayinfo.data.local.datasource.LocalDataSource
import com.josycom.mayorjay.holidayinfo.data.mapper.toCountry
import com.josycom.mayorjay.holidayinfo.data.mapper.toCountryEntity
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.remote.result.HolidayApiResult
import com.josycom.mayorjay.holidayinfo.data.remote.datasource.RemoteDataSource
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import javax.inject.Inject

class HolidayRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource) : HolidayRepository {

    private val countryErrorHandler = MutableLiveData<HolidayApiResult>()

    override fun getCountriesLocal(): LiveData<List<Country>> {
        return localDataSource.getCountries()
            .map { countries -> countries.map { country -> country.toCountry() } }
    }

    override suspend fun getCountriesRemote() {
        try {
            val countries = remoteDataSource.getCountries()
            localDataSource.saveCountries(countries.map { it.toCountryEntity() })
        } catch (ex: Exception) {
            countryErrorHandler.value = HolidayApiResult.Error(ex)
        }
    }

    override suspend fun getHolidaysRemote(holidayRequest: HolidayRequest) {
        remoteDataSource.getHolidays(holidayRequest)
    }

    override fun getApiResult(): LiveData<HolidayApiResult> {
        return remoteDataSource.getApiResult()
    }

    override fun getCountryErrorHandler(): MutableLiveData<HolidayApiResult> {
        return countryErrorHandler
    }
}