package com.josycom.mayorjay.holidayinfo.viemodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.repository.HolidayInfoRepository
import com.josycom.mayorjay.holidayinfo.util.UIState
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
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
    private lateinit var repository: HolidayInfoRepository
    @InjectMocks
    private lateinit var sut: OverviewViewModel

    @Test
    fun `test getCountries_Exception thrown by Repository_UiState LiveData has Error state`() = runBlocking {
        Mockito.`when`(repository.getCountries()).thenReturn(Result.failure(Exception("")))

        sut.getCountries()
        val result = sut.getUiState().value
        assertTrue(result is UIState.Error)
    }

    @Test
    fun `test getCountries_Success returned by Repository_UiState LiveData has Success state`() = runBlocking {
        Mockito.`when`(repository.getCountries()).thenReturn(Result.success(emptyList()))

        sut.getCountries()
        val result = sut.getUiState().value
        assertTrue(result is UIState.Success)
    }

    @Test
    fun `test getCountries_Success returned by Repository_UiState LiveData has Success state with valid data`() = runBlocking {
        val country = Country("DE", "Germany")
        Mockito.`when`(repository.getCountries()).thenReturn(Result.success(listOf(country)))

        sut.getCountries()
        val result = sut.getUiState().value
        assertTrue((result as UIState.Success).data.isNotEmpty())
    }
}