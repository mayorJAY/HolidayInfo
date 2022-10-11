package com.josycom.mayorjay.holidayinfo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class CountryEntity(
    @PrimaryKey
    val code: String,
    val name: String)
