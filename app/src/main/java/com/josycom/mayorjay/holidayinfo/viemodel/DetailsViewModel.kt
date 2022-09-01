package com.josycom.mayorjay.holidayinfo.viemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.josycom.mayorjay.holidayinfo.model.HolidayRepository
import com.josycom.mayorjay.holidayinfo.model.remote.HolidayApiResult
import com.josycom.mayorjay.holidayinfo.model.remote.models.HolidayRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: HolidayRepository) : ViewModel() {

    val apiResult: LiveData<HolidayApiResult> = repository.getApiResult()

    fun getHolidays(holidayRequest: HolidayRequest) {
        repository.getHolidays(holidayRequest)
    }
}