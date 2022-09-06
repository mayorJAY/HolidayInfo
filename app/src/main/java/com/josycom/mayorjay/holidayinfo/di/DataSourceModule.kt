package com.josycom.mayorjay.holidayinfo.di

import com.josycom.mayorjay.holidayinfo.data.local.datasource.LocalDataSource
import com.josycom.mayorjay.holidayinfo.data.local.datasource.LocalDataSourceImpl
import com.josycom.mayorjay.holidayinfo.data.remote.datasource.RemoteDataSource
import com.josycom.mayorjay.holidayinfo.data.remote.datasource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {

    @Binds
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource
}