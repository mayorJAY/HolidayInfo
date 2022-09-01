package com.josycom.mayorjay.holidayinfo.di

import com.josycom.mayorjay.holidayinfo.model.HolidayRepository
import com.josycom.mayorjay.holidayinfo.model.remote.RemoteHolidayRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindHolidayRepository(holidayRepositoryImpl: RemoteHolidayRepositoryImpl): HolidayRepository
}