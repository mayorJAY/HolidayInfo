package com.josycom.mayorjay.holidayinfo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val canProceed = MutableLiveData<Boolean>()

    fun validateInputs(email: String, password: String): Boolean {
        return email.matches(".+@.+\\.[a-z]+".toRegex()) && password.length >= 6
    }
}