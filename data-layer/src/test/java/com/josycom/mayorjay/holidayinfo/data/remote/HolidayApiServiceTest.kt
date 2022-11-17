package com.josycom.mayorjay.holidayinfo.data.remote

import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.data.remote.service.HolidayApiService
import com.josycom.mayorjay.holidayinfo.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class HolidayApiServiceTest : TestCase() {

    private lateinit var service: HolidayApiService
    private lateinit var server: MockWebServer

    override fun setUp() {
        server = MockWebServer()
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        service = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(HolidayApiService::class.java)
    }

    fun `test getCountries request_sent valid_path_called`() {
        runBlocking {
            enqueueMockResponse("CountryResponse.json")
            service.getCountries()
            val request = server.takeRequest()
            assertEquals(request.path, Constants.COUNTRY_ENDPOINT)
        }
    }

    fun `test getCountries request_sent valid_response_received`() {
        runBlocking {
            enqueueMockResponse("CountryResponse.json")
            val responseBody = service.getCountries()
            assertNotNull(responseBody)
        }
    }

    fun `test getCountries request_sent valid_country_code_received_from_response`() {
        runBlocking {
            enqueueMockResponse("CountryResponse.json")
            val responseBody = service.getCountries()
            val firstItem = responseBody[0]
            assertTrue(firstItem.code.length == 2)
        }
    }

    fun `test getHolidays request_sent valid_path_called`() {
        runBlocking {
            enqueueMockResponse("HolidayResponse.json")
            val holidayRequest = HolidayRequest("GB", "2022")
            service.getHolidays(holidayRequest.year.toInt(), holidayRequest.countryCode)
            val request = server.takeRequest()
            assertTrue(request.path?.contains("PublicHolidays") ?: false)
        }
    }

    fun `test getHolidays request_sent valid_response_received`() {
        runBlocking {
            enqueueMockResponse("HolidayResponse.json")
            val holidayRequest = HolidayRequest("GB", "2022")
            val responseBody = service.getHolidays(holidayRequest.year.toInt(), holidayRequest.countryCode)
            assertNotNull(responseBody)
        }
    }

    fun `test getHolidays valid_request_sent valid_year_received_from_response`() {
        runBlocking {
            enqueueMockResponse("HolidayResponse.json")
            val holidayRequest = HolidayRequest("GB", "2022")
            val responseBody = service.getHolidays(holidayRequest.year.toInt(), holidayRequest.countryCode)
            val firstItem = responseBody[0]
            assertTrue(firstItem.date.contains("2022"))
        }
    }

    fun `test getHolidays valid_request_sent valid_country_received_from_response`() {
        runBlocking {
            enqueueMockResponse("HolidayResponse.json")
            val holidayRequest = HolidayRequest("GB", "2022")
            val responseBody = service.getHolidays(holidayRequest.year.toInt(), holidayRequest.countryCode)
            val firstItem = responseBody[0]
            assertTrue(firstItem.countryCode == "GB")
        }
    }

    private fun enqueueMockResponse(fileName: String) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            server.enqueue(mockResponse)
        }
    }

    override fun tearDown() {
        server.shutdown()
    }
}