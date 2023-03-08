package com.josycom.mayorjay.holidayinfo.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.josycom.mayorjay.holidayinfo.data.local.entity.CountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountries(countries: List<CountryEntity>)

    @Query("SELECT * FROM country ORDER BY code ASC")
    fun getCountries(): Flow<List<CountryEntity>>

    @Query("DELETE FROM country")
    suspend fun deleteAllCountries()
}