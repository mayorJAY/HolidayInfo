package com.josycom.mayorjay.holidayinfo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.josycom.mayorjay.holidayinfo.data.local.entity.CountryEntity

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryEntity>)

    @Query("SELECT * FROM country ORDER BY code ASC")
    suspend fun getCountries(): List<CountryEntity>

    @Query("SELECT count(*) FROM country")
    suspend fun getCountryCount(): Long
}