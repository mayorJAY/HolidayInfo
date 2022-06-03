package com.josycom.mayorjay.holidayinfo.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josycom.mayorjay.holidayinfo.network.HolidayApi
import com.josycom.mayorjay.holidayinfo.network.HolidayApiStatus
import com.josycom.mayorjay.holidayinfo.network.model.Holiday
import com.josycom.mayorjay.holidayinfo.network.model.HolidayRequest
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailsViewModel : ViewModel() {
    private val _status = MutableLiveData<HolidayApiStatus>()
    val status: LiveData<HolidayApiStatus> = _status
    private val _holidays = MutableLiveData<List<Holiday>>()
    val holidays: LiveData<List<Holiday>> = _holidays

    fun getHolidays(holidayRequest: HolidayRequest) {
        viewModelScope.launch {
            _status.value = HolidayApiStatus.LOADING
            try {
                val listResult = HolidayApi.retrofitService.getHolidays(holidayRequest)
                _holidays.value = listResult.holidays
                _status.value = HolidayApiStatus.DONE
            } catch (ex: Exception) {
                Timber.e(ex)
                _status.value = HolidayApiStatus.ERROR
                _holidays.value = listOf()
            }
        }
    }
}