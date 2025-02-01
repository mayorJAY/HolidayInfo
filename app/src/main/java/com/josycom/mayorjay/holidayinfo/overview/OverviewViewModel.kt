package com.josycom.mayorjay.holidayinfo.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.repository.HolidayInfoRepository
import com.josycom.mayorjay.holidayinfo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(private val repository: HolidayInfoRepository) : ViewModel() {

    private var _uiData: MutableLiveData<Resource<List<Country>>> = MutableLiveData()
    val uiData: LiveData<Resource<List<Country>>> = _uiData

    private val _showPopup: MutableLiveData<Boolean> = MutableLiveData(false)
    val showPopup: LiveData<Boolean> = _showPopup

    private val _country: MutableLiveData<String> = MutableLiveData("")
    val country: LiveData<String> = _country

    init {
        getCountries()
    }

    fun getCountries() {
        viewModelScope.launch {
            val countryFlow = repository.getCountries()
            countryFlow.collect { resource ->
                _uiData.value = resource
            }
        }
    }

    fun populateYearList(): MutableList<String> {
        val yearList = mutableListOf<String>()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val firstYear = currentYear - 50
        val lastYear = currentYear + 50
        for (year in firstYear..lastYear) {
            yearList.add(year.toString())
        }
        return yearList
    }

    fun updatePopup(value: Boolean) {
        _showPopup.value = value
    }

    fun updateCountry(value: String) {
        _country.value = value
        updatePopup(true)
    }
}