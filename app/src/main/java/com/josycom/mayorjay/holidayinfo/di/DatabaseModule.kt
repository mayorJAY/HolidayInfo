package com.josycom.mayorjay.holidayinfo.di

import android.content.Context
import androidx.room.Room
import com.josycom.mayorjay.holidayinfo.data.local.dao.CountryDao
import com.josycom.mayorjay.holidayinfo.data.local.db.HolidayInfoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    fun provideCountryDao(database: HolidayInfoDatabase): CountryDao = database.getCountryDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): HolidayInfoDatabase {
        return Room.databaseBuilder(
            appContext,
            HolidayInfoDatabase::class.java,
            "HolidayInfo.db")
            .build()
    }


}