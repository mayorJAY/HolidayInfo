package com.josycom.mayorjay.holidayinfo.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josycom.mayorjay.holidayinfo.network.HolidayApi
import com.josycom.mayorjay.holidayinfo.network.HolidayApiStatus
import com.josycom.mayorjay.holidayinfo.network.model.Country
import kotlinx.coroutines.launch
import timber.log.Timber

class OverviewViewModel : ViewModel() {
    private val _status = MutableLiveData<HolidayApiStatus>()
    val status: LiveData<HolidayApiStatus> = _status
    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = _countries
    var yearSelected: String? = null

    init {
        getCountries()
    }

    fun getCountries() {
        viewModelScope.launch {
            _status.value = HolidayApiStatus.LOADING
            try {
                val listResult = HolidayApi.retrofitService.getCountries()
                _countries.value = listResult.countries
                _status.value = HolidayApiStatus.DONE
            } catch (ex: Exception) {
                Timber.e(ex)
                _status.value = HolidayApiStatus.ERROR
                _countries.value = listOf()
            }
        }
    }
}