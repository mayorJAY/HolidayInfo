package com.josycom.mayorjay.holidayinfo.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.josycom.mayorjay.holidayinfo.data.model.Holiday
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.data.repository.HolidayInfoRepository
import com.josycom.mayorjay.holidayinfo.details.DetailsViewModel
import com.josycom.mayorjay.holidayinfo.util.Resource
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
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
class DetailsViewModelTest: TestCase() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var repository: HolidayInfoRepository
    @InjectMocks
    private lateinit var sut: DetailsViewModel
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
    fun `test getHolidays_Success returned by Repository_UiState LiveData has Success state`() = runBlocking {
        val request = HolidayRequest("NG", "2021")
        Mockito.`when`(repository.getHolidays(request)).thenReturn(flowOf(Resource.Success(emptyList())))

        sut.getHolidays(request)
        val result = sut.uiData.value
        assertTrue(result is Resource.Success)
    }

    @Test
    fun `test getHolidays_Error thrown by Repository_UiState LiveData has Error state`() = runBlocking {
        val request = HolidayRequest("NG", "2022")
        Mockito.`when`(repository.getHolidays(request)).thenReturn(flow { emit(Resource.Error(Exception(""))) })

        sut.getHolidays(request)
        val result = sut.uiData.value
        assertTrue(result is Resource.Error)
    }

    @Test
    fun `test getHolidays_Success returned by Repository_UiState LiveData has Success state with valid data`() = runBlocking {
        val request = HolidayRequest("NG", "2022")
        val holiday = Holiday("1st Jan", "New Year", "New Year", "NG", emptyList(), emptyList())
        Mockito.`when`(repository.getHolidays(request)).thenReturn(flowOf(Resource.Success(listOf(holiday))))

        sut.getHolidays(request)
        val result = sut.uiData.value
        assertTrue(((result as Resource.Success).data ?: emptyList()).isNotEmpty())
    }
}