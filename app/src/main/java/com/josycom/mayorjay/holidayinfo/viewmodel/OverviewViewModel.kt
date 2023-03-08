package com.josycom.mayorjay.holidayinfo.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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

    private var _uiData: LiveData<Resource<List<Country>>> = MutableLiveData()

    fun getCountries() {
        viewModelScope.launch {
            _uiData = repository.getCountries().asLiveData()
        }
    }

    fun getUiData() = _uiData

    fun getYearList(): MutableList<String> {
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