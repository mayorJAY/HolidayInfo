package com.josycom.mayorjay.holidayinfo.data.local.datasource

import androidx.room.withTransaction
import com.josycom.mayorjay.holidayinfo.data.local.db.HolidayInfoDatabase
import com.josycom.mayorjay.holidayinfo.data.mapper.toCountry
import com.josycom.mayorjay.holidayinfo.data.mapper.toCountryEntity
import com.josycom.mayorjay.holidayinfo.data.model.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val database: HolidayInfoDatabase): LocalDataSource {

    private val countryDao = database.getCountryDao()

    override fun getCountries(): Flow<List<Country>> {
        return countryDao.getCountries()
            .map { countries ->
                countries.map { countryEntity ->
                    countryEntity.toCountry()
                }
            }
    }

    override suspend fun saveCountries(countries: List<Country>) {
        database.withTransaction {
            countryDao.deleteAllCountries()
            countryDao.insertCountries(countries.map { country -> country.toCountryEntity() })
        }
    }
}