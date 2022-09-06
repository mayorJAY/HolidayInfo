package com.josycom.mayorjay.holidayinfo.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.josycom.mayorjay.holidayinfo.data.local.dao.CountryDao
import com.josycom.mayorjay.holidayinfo.data.local.entity.CountryEntity

@Database(entities = [CountryEntity::class], version = 1, exportSchema = false)
abstract class HolidayInfoDatabase : RoomDatabase() {

    abstract fun getCountryDao(): CountryDao
}