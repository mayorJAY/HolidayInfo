package com.josycom.mayorjay.holidayinfo.viemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.josycom.mayorjay.holidayinfo.model.HolidayRepository
import com.josycom.mayorjay.holidayinfo.model.remote.HolidayApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(private val repository: HolidayRepository) : ViewModel() {

    val apiResult: LiveData<HolidayApiResult> = repository.getApiResult()
    var yearSelected: String? = null

    init {
        getCountries()
    }

    fun getCountries() {
        repository.getCountries()
    }
}