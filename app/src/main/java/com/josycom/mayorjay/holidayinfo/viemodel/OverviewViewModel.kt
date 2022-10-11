package com.josycom.mayorjay.holidayinfo.viemodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.repository.HolidayInfoRepository
import com.josycom.mayorjay.holidayinfo.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(private val repository: HolidayInfoRepository) : ViewModel() {

    var yearSelected: String? = null
    private val _uiState: MutableLiveData<UIState<List<Country>>> = MutableLiveData()

    fun getCountries() {
        viewModelScope.launch {
            _uiState.postValue(UIState.Loading)

            val result = repository.getCountries()
            val state: UIState<List<Country>> = if (result.isSuccess) {
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