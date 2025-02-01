package com.josycom.mayorjay.holidayinfo.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.repository.HolidayInfoRepository
import com.josycom.mayorjay.holidayinfo.overview.OverviewViewModel
import com.josycom.mayorjay.holidayinfo.util.Resource
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
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
    private val dispatcher = UnconfinedTestDispatcher()

    @Before
    fun before() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test getCountries_Exception thrown by Repository_UiState LiveData has Error state`() = runBlocking {
        Mockito.`when`(repository.getCountries()).thenReturn(flowOf(Resource.Error(Exception(""))))

        sut.getCountries()
        val result = sut.uiData.value
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `test getCountries_Success returned by Repository_UiState LiveData has Success state`() = runBlocking {
        Mockito.`when`(repository.getCountries()).thenReturn(flowOf(Resource.Success(emptyList())))

        sut.getCountries()
        val result = sut.uiData.value
        assertTrue(result is Resource.Success)
    }

    @Test
    fun `test getCountries_Success returned by Repository_UiState LiveData has Success state with valid data`() = runBlocking {
        val country = Country("DE", "Germany")
        Mockito.`when`(repository.getCountries()).thenReturn(flowOf(Resource.Success(listOf(country))))

        sut.getCountries()
        val result = sut.uiData.value
        assertTrue(((result as Resource.Success).data ?: emptyList()).isNotEmpty())
    }
}