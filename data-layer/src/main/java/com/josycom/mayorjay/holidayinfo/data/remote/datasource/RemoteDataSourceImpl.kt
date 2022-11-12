package com.josycom.mayorjay.holidayinfo.data.remote.datasource

import com.josycom.mayorjay.holidayinfo.data.mapper.toCountry
import com.josycom.mayorjay.holidayinfo.data.mapper.toHoliday
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.model.Holiday
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.data.remote.service.HolidayApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: HolidayApiService,
    private val dispatcher: CoroutineDispatcher): RemoteDataSource {

    override suspend fun getCountries(): Result<List<Country>> {
        return try {
            val countries = withContext(dispatcher) {
                apiService.getCountries()
            }.map { countryRemote -> countryRemote.toCountry() }
            Result.success(countries)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }

    override suspend fun getHolidays(holidayRequest: HolidayRequest): Result<List<Holiday>> {
        return try {
            val holidays = withContext(dispatcher) {
                apiService.getHolidays(holidayRequest.year.toInt(), holidayRequest.countryCode)
            }.map { holidayRemote -> holidayRemote.toHoliday() }
            Result.success(holidays)
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}