package com.josycom.mayorjay.holidayinfo.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.josycom.mayorjay.holidayinfo.model.HolidayRepository

class ViewModelProviderFactory(private val repository: HolidayRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(OverviewViewModel::class.java) -> { OverviewViewModel(repository) as T }
            modelClass.isAssignableFrom(DetailsViewModel::class.java) -> { DetailsViewModel(repository) as T }
            else -> throw IllegalArgumentException("Unknown class")
        }
    }
}