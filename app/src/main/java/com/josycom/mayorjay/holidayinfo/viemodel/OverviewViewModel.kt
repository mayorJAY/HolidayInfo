package com.josycom.mayorjay.holidayinfo.viemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josycom.mayorjay.holidayinfo.model.network.HolidayApiStatus
import com.josycom.mayorjay.holidayinfo.model.HolidayRepository
import com.josycom.mayorjay.holidayinfo.model.network.models.Country
import kotlinx.coroutines.launch
import timber.log.Timber

class OverviewViewModel(private val repository: HolidayRepository) : ViewModel() {
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
                val listResult = repository.getCountries()
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