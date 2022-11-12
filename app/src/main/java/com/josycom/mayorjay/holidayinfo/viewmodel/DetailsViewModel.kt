package com.josycom.mayorjay.holidayinfo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.model.Holiday
import com.josycom.mayorjay.holidayinfo.data.repository.HolidayInfoRepository
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: HolidayInfoRepository) : ViewModel() {

    private var _country = Country()
    var country: Country
        get() = _country
        set(value) { _country = value }

    private var _yearSelected: String = ""
    var yearSelected: String
        get() = _yearSelected
        set(value) { _yearSelected = value }

    private val _uiState: MutableLiveData<UIState<List<Holiday>>> = MutableLiveData()

    fun getHolidays(holidayRequest: HolidayRequest) {
        viewModelScope.launch {
            _uiState.postValue(UIState.Loading)

            val result = repository.getHolidays(holidayRequest)
            val state: UIState<List<Holiday>> = if (result.isSuccess) {
                UIState.Success(result.getOrDefault(emptyList()))
            } else {
                result.exceptionOrNull()?.let {
                    Timber.e(it)
                }
                UIState.Error()
            }
            _uiState.postValue(state)
        }
    }

    fun getUiState() = _uiState
}