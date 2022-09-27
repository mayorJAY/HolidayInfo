package com.josycom.mayorjay.holidayinfo.data.remote.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.josycom.mayorjay.holidayinfo.data.mapper.toHoliday
import com.josycom.mayorjay.holidayinfo.data.remote.result.HolidayApiResult
import com.josycom.mayorjay.holidayinfo.data.remote.service.HolidayApiService
import com.josycom.mayorjay.holidayinfo.data.remote.models.CountryRemote
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: HolidayApiService,
    private val dispatcher: CoroutineDispatcher): RemoteDataSource {

    private val apiResult = MutableLiveData<HolidayApiResult>(HolidayApiResult.Loading)

    override suspend fun getCountries(): List<CountryRemote> {
        return try {
            withContext(dispatcher) {
                apiService.getCountries()
            }.countries
        } catch (ex: Exception) {
            throw ex
        }
    }

    override suspend fun getHolidays(holidayRequest: HolidayRequest) {
        withContext(dispatcher) {
            apiResult.postValue(HolidayApiResult.Loading)
            try {
                val response = apiService.getHolidays(holidayRequest)
                val holidays = response.holidays
                apiResult.postValue(HolidayApiResult.Success(holidays.map { it.toHoliday() }))
            } catch (ex: Exception) {
                Timber.e(ex)
                apiResult.postValue(HolidayApiResult.Error(ex))
            }
        }
    }

    override fun getApiResult(): LiveData<HolidayApiResult> {
        return apiResult
    }
}