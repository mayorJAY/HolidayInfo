package com.josycom.mayorjay.holidayinfo.viemodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.josycom.mayorjay.holidayinfo.data.model.Holiday
import com.josycom.mayorjay.holidayinfo.data.remote.result.HolidayApiResult
import com.josycom.mayorjay.holidayinfo.data.repository.HolidayRepository
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailsViewModelTest: TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var repository: HolidayRepository
    @InjectMocks
    private lateinit var sut: DetailsViewModel
    @Mock
    private lateinit var observer: Observer<HolidayApiResult>

    @Test
    fun `test getApiResult_initial state of network call_HolidayApiResult#Loading returned`() {
        try {
            Mockito.`when`(repository.getApiResult()).thenReturn(MutableLiveData(HolidayApiResult.Loading))
            sut.getApiResult().observeForever(observer)

            val result = sut.getApiResult().value
            assertEquals(result, HolidayApiResult.Loading)
        } finally {
            sut.getApiResult().removeObserver(observer)
        }
    }

    @Test
    fun `test getApiResult_failed network call_HolidayApiResult#Error returned`() {
        try {
            Mockito.`when`(repository.getApiResult()).thenReturn(MutableLiveData(HolidayApiResult.Error(Throwable("Network Error"))))
            sut.getApiResult().observeForever(observer)

            val result = sut.getApiResult().value
            assertTrue(result is HolidayApiResult.Error)
        } finally {
            sut.getApiResult().removeObserver(observer)
        }
    }

    @Test
    fun `test getApiResult_success network call_HolidayApiResult#Success returned`() {
        try {
            Mockito.`when`(repository.getApiResult()).thenReturn(MutableLiveData(HolidayApiResult.Success(emptyList())))
            sut.getApiResult().observeForever(observer)

            val result = sut.getApiResult().value
            assertTrue(result is HolidayApiResult.Success)
        } finally {
            sut.getApiResult().removeObserver(observer)
        }
    }

    @Test
    fun `test getApiResult_success network call_valid data returned`() {
        try {
            val list = listOf(Holiday("Mar 20 2022", "Germany", "Deutsch", "DE", emptyList(), emptyList()))
            Mockito.`when`(repository.getApiResult()).thenReturn(MutableLiveData(HolidayApiResult.Success(list)))
            sut.getApiResult().observeForever(observer)

            val result = sut.getApiResult().value
            assertTrue((result as HolidayApiResult.Success).data.isNotEmpty())
        } finally {
            sut.getApiResult().removeObserver(observer)
        }
    }
}