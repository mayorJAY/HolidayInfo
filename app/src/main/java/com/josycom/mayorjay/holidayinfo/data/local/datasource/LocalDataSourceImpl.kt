package com.josycom.mayorjay.holidayinfo.data.local.datasource

import androidx.lifecycle.LiveData
import com.josycom.mayorjay.holidayinfo.data.local.dao.CountryDao
import com.josycom.mayorjay.holidayinfo.data.local.entity.CountryEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val countryDao: CountryDao): LocalDataSource {

    override fun getCountries(): LiveData<List<CountryEntity>> {
        return countryDao.getCountries()
    }

    override suspend fun saveCountries(countries: List<CountryEntity>) {
        try {
            countryDao.insertCountries(countries)
        } catch (ex: Exception) {
            throw ex
        }
    }
}