package com.josycom.mayorjay.holidayinfo.login

import junit.framework.TestCase

class LoginViewModelTest : TestCase() {

    private lateinit var sut: LoginViewModel

    override fun setUp() {
        sut = LoginViewModel()
    }

    fun `test validateInputs empty_values_passed result_return_should_be_invalid`() {
        val result = sut.validateInputs("", "")
        assertFalse(result)
    }

    fun `test validateInputs wrong_email_format_passed result_return_should_be_invalid`() {
        val result = sut.validateInputs("email@.com", "qwedvs")
        assertFalse(result)
    }

    fun `test validateInputs wrong_password_length_passed result_return_should_be_invalid`() {
        val result = sut.validateInputs("email@test.com", "asd")
        assertFalse(result)
    }

    fun `test validateInputs valid_email_and_password_passed result_return_should_be_valid`() {
        val result = sut.validateInputs("email@test.com", "asdasdfghgfvdc")
        assertTrue(result)
    }
}