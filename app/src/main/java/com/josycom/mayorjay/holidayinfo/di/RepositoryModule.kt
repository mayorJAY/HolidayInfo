package com.josycom.mayorjay.holidayinfo.di

import com.josycom.mayorjay.holidayinfo.data.repository.HolidayInfoRepository
import com.josycom.mayorjay.holidayinfo.data.repository.HolidayInfoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindHolidayRepository(holidayInfoRepositoryImpl: HolidayInfoRepositoryImpl): HolidayInfoRepository
}