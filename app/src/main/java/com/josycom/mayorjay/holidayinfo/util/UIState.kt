package com.josycom.mayorjay.holidayinfo.util

sealed class UIState<out T> {

    object Loading: UIState<Nothing>()
    data class Success<out T>(val data: T): UIState<T>()
    data class Error(val error: Throwable? = null): UIState<Nothing>()
}