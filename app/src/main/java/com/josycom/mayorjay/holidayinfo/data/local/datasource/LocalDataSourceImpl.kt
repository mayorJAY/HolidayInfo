package com.josycom.mayorjay.holidayinfo.data.local.datasource

import com.josycom.mayorjay.holidayinfo.data.local.dao.CountryDao
import com.josycom.mayorjay.holidayinfo.data.mapper.toCountry
import com.josycom.mayorjay.holidayinfo.data.mapper.toCountryEntity
import com.josycom.mayorjay.holidayinfo.data.model.Country
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val countryDao: CountryDao,
    private val dispatcher: CoroutineDispatcher): LocalDataSource {

    override suspend fun getCountries(): Result<List<Country>> {
        return try {
            val countries = withContext(dispatcher) {
                    countryDao.getCountries()
                }.map { countryEntity -> countryEntity.toCountry() }
            Result.success(countries)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun saveCountries(countries: List<Country>) {
        try {
            withContext(dispatcher) {
                countryDao.insertCountries(countries.map { country -> country.toCountryEntity() })
            }
        } catch (ex: Exception) {
            throw ex
        }
    }

    override suspend fun isEmpty(): Boolean = countryDao.getCountryCount() == 0L
}