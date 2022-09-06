package com.josycom.mayorjay.holidayinfo.viemodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.repository.HolidayRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(private val repository: HolidayRepository) : ViewModel() {

    var yearSelected: String? = null

    init {
        getCountries()
    }

    fun getCountriesLocal(): LiveData<List<Country>> = repository.getCountriesLocal()

    fun getCountries() {
        viewModelScope.launch {
            repository.getCountriesRemote()
        }
    }
}