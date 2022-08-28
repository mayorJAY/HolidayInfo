package com.josycom.mayorjay.holidayinfo.model.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.josycom.mayorjay.holidayinfo.model.HolidayRepository
import com.josycom.mayorjay.holidayinfo.model.local.CountryLocal
import com.josycom.mayorjay.holidayinfo.model.local.HolidayLocal
import com.josycom.mayorjay.holidayinfo.model.remote.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.model.util.toCountryLocal
import com.josycom.mayorjay.holidayinfo.model.util.toHolidayLocal
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class RemoteHolidayRepositoryImpl(private val apiService: HolidayApiService) : HolidayRepository {

    private val _apiResult = MutableLiveData<HolidayApiResult>(HolidayApiResult.Loading)
    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun getCountries() {
        scope.launch {
            _apiResult.value = HolidayApiResult.Loading
            try {
                val response = apiService.getCountries()
                val countries = response.countries
                val list = mutableListOf<CountryLocal>()
                for (item in countries) {
                    list.add(item.toCountryLocal())
                }
                _apiResult.value = HolidayApiResult.Success(list)
            } catch (ex: Exception) {
                Timber.e(ex)
                _apiResult.value = HolidayApiResult.Error(ex)
            }
        }
    }

    override fun getHolidays(holidayRequest: HolidayRequest) {
        scope.launch {
            _apiResult.value = HolidayApiResult.Loading
            try {
                val response = apiService.getHolidays(holidayRequest)
                val holidays = response.holidays
                val list = mutableListOf<HolidayLocal>()
                for (item in holidays) {
                    list.add(item.toHolidayLocal())
                }
                _apiResult.value = HolidayApiResult.Success(list)
            } catch (ex: Exception) {
                Timber.e(ex)
                _apiResult.value = HolidayApiResult.Error(ex)
            }
        }
    }

    override fun getApiResult(): LiveData<HolidayApiResult> {
        return _apiResult
    }
}