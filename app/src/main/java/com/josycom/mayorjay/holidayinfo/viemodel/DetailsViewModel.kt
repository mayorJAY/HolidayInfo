package com.josycom.mayorjay.holidayinfo.viemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josycom.mayorjay.holidayinfo.data.repository.HolidayRepository
import com.josycom.mayorjay.holidayinfo.data.remote.result.HolidayApiResult
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: HolidayRepository) : ViewModel() {

    fun getApiResult(): LiveData<HolidayApiResult> = repository.getApiResult()

    fun getHolidays(holidayRequest: HolidayRequest) {
        viewModelScope.launch {
            repository.getHolidaysRemote(holidayRequest)
        }
    }
}