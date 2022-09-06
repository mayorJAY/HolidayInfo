package com.josycom.mayorjay.holidayinfo.viemodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.repository.HolidayRepository
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class OverviewViewModelTest: TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var repository: HolidayRepository
    @InjectMocks
    private lateinit var sut: OverviewViewModel
    @Mock
    private lateinit var observer: Observer<List<Country>>

    @Test
    fun `test getCountriesLocal_no list of Country returned by Repository_LiveData has null value`() {
        try {
            Mockito.`when`(repository.getCountriesLocal()).thenReturn(MutableLiveData())
            sut.getCountriesLocal().observeForever(observer)

            val result = sut.getCountriesLocal().value
            assertEquals(result, null)
        } finally {
            sut.getCountriesLocal().removeObserver(observer)
        }
    }

    @Test
    fun `test getCountriesLocal_empty list of Country returned by Repository_LiveData has empty list`() {
        try {
            Mockito.`when`(repository.getCountriesLocal()).thenReturn(MutableLiveData(listOf()))
            sut.getCountriesLocal().observeForever(observer)

            val result = sut.getCountriesLocal().value
            assertEquals(result?.size, 0)
        } finally {
            sut.getCountriesLocal().removeObserver(observer)
        }
    }

    @Test
    fun `test getCountriesLocal_valid list of Country returned by Repository_LiveData has valid list of Country`() {
        try {
            val list = listOf(Country("NG", "Nigeria"), Country("DE", "Germany"), Country("EE", "Estonia"))
            Mockito.`when`(repository.getCountriesLocal()).thenReturn(MutableLiveData(list))
            sut.getCountriesLocal().observeForever(observer)

            val result = sut.getCountriesLocal().value
            assertEquals(result, list)
        } finally {
            sut.getCountriesLocal().removeObserver(observer)
        }
    }

    @Test
    fun `test getCountriesLocal_valid list of Country returned by Repository_LiveData has a valid Country item`() {
        try {
            val list = listOf(Country("NG", "Nigeria"), Country("DE", "Germany"), Country("EE", "Estonia"))
            Mockito.`when`(repository.getCountriesLocal()).thenReturn(MutableLiveData(list))
            sut.getCountriesLocal().observeForever(observer)

            val result = sut.getCountriesLocal().value ?: listOf()
            assertEquals(result[1], Country("DE", "Germany"))
        } finally {
            sut.getCountriesLocal().removeObserver(observer)
        }
    }
}