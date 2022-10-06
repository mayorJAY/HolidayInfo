package com.josycom.mayorjay.holidayinfo.data.repository

import com.josycom.mayorjay.holidayinfo.data.local.datasource.LocalDataSource
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.model.Holiday
import com.josycom.mayorjay.holidayinfo.data.remote.datasource.RemoteDataSource
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import javax.inject.Inject

class HolidayInfoRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource) : HolidayInfoRepository {

    override suspend fun getCountries(): Result<List<Country>> {
        return try {
            localDataSource.ensureIsNotEmpty().getCountries()
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    private suspend fun LocalDataSource.ensureIsNotEmpty() = apply {
        try {
            if (isEmpty()) {
                val result = remoteDataSource.getCountries()
                if (result.isSuccess) {
                    val countries = result.getOrDefault(emptyList())
                    saveCountries(countries)
                } else {
                    result.exceptionOrNull()?.let {
                        throw it
                    }
                }
            }
        } catch (ex: Exception) {
            throw ex
        }
    }

    override suspend fun getHolidays(holidayRequest: HolidayRequest): Result<List<Holiday>> {
        return try {
            remoteDataSource.getHolidays(holidayRequest)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}