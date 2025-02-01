package com.josycom.mayorjay.holidayinfo.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josycom.mayorjay.holidayinfo.data.model.Holiday
import com.josycom.mayorjay.holidayinfo.data.repository.HolidayInfoRepository
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: HolidayInfoRepository) : ViewModel() {

    private var _uiData: MutableLiveData<Resource<List<Holiday>>> = MutableLiveData()
    val uiData: LiveData<Resource<List<Holiday>>> = _uiData

    fun getHolidays(holidayRequest: HolidayRequest) {
        viewModelScope.launch {
            val holidayFlow = repository.getHolidays(holidayRequest)
            holidayFlow.collect { resource ->
                _uiData.value = resource
            }
        }
    }
}