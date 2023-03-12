package com.josycom.mayorjay.holidayinfo.viewmodel

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

    private var _yearSelected: String? = null
    var yearSelected: String?
        get() = _yearSelected
        set(value) { _yearSelected = value }

    private var _yearList = listOf<String>()
    var yearList: List<String>
        get() = _yearList
        set(value) { _yearList = value }

    private var _uiData: MutableLiveData<Resource<List<Country>>> = MutableLiveData()

    init {
        yearList = populateYearList()
    }

    fun getCountries() {
        viewModelScope.launch {
            val countryFlow = repository.getCountries()
            countryFlow.collect { resource ->
                _uiData.value = resource
            }
        }
    }

    fun getUiData() = _uiData

    private fun populateYearList(): MutableList<String> {
        val yearList = mutableListOf<String>()
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val firstYear = currentYear - 100
        val lastYear = currentYear + 100
        for (year in firstYear..lastYear) {
            yearList.add(year.toString())
        }
        return yearList
    }
}