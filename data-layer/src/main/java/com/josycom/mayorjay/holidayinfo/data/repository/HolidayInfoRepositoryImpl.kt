package com.josycom.mayorjay.holidayinfo.data.repository

import com.josycom.mayorjay.holidayinfo.data.local.datasource.LocalDataSource
import com.josycom.mayorjay.holidayinfo.data.model.Country
import com.josycom.mayorjay.holidayinfo.data.model.Holiday
import com.josycom.mayorjay.holidayinfo.data.remote.datasource.RemoteDataSource
import com.josycom.mayorjay.holidayinfo.data.remote.models.HolidayRequest
import com.josycom.mayorjay.holidayinfo.util.NetworkHelper
import com.josycom.mayorjay.holidayinfo.util.Resource
import com.josycom.mayorjay.holidayinfo.util.networkBoundResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class HolidayInfoRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val networkHelper: NetworkHelper,
    private val dispatcher: CoroutineDispatcher
) : HolidayInfoRepository {

    override suspend fun getCountries(): Flow<Resource<List<Country>>> = networkBoundResource(
        query = {
            localDataSource.getCountries()
        },
        fetch = {
            delay(2000)
            remoteDataSource.getCountries()
        },
        saveFetchResult = { countries ->
            localDataSource.saveCountries(countries)
        },
        shouldFetch = {
            networkHelper.isNetworkAvailable()
        },
        onFetchFailed = { ex ->
            Timber.e(ex)
        },
        dispatcher = dispatcher
    )

    override suspend fun getHolidays(holidayRequest: HolidayRequest): Flow<Resource<List<Holiday>>> {
        return flow {
            emit(Resource.Loading())

            try {
                val holidays = remoteDataSource.getHolidays(holidayRequest)
                emit(Resource.Success(holidays))
            } catch (ex: Exception) {
                Timber.e(ex)
                emit(Resource.Error(ex))
            }
        }.flowOn(dispatcher)
    }
}