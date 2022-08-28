package com.josycom.mayorjay.holidayinfo.model.util

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.josycom.mayorjay.holidayinfo.R
import com.josycom.mayorjay.holidayinfo.model.local.CountryLocal
import com.josycom.mayorjay.holidayinfo.model.local.HolidayLocal
import com.josycom.mayorjay.holidayinfo.model.remote.models.CountryRemote
import com.josycom.mayorjay.holidayinfo.model.remote.models.HolidayRemote
import timber.log.Timber
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

fun Fragment.switchFragment(destination: Fragment, bundle: Bundle?, addToBackStack: Boolean) {
    this.parentFragmentManager.beginTransaction().apply {
        if (bundle != null) destination.arguments = bundle
        replace(R.id.mainFragment, destination)
        if (addToBackStack) addToBackStack(null)
        commit()
    }
}

fun List<String>.getJoinedString(): String = this.joinToString(", ")

fun String.getFormattedDate(fromFormat: SimpleDateFormat, toFormat: SimpleDateFormat): String {
    return try {
        toFormat.format(fromFormat.parse(this) ?: Date())
    } catch (e: ParseException) {
        Timber.e(e)
        this
    }
}

fun CountryRemote.toCountryLocal() = CountryLocal(code = this.code, name = this.name)

fun HolidayRemote.toHolidayLocal(): HolidayLocal {
    return HolidayLocal(
        date = this.date,
        name = this.name,
        localName = this.localName,
        countryCode = this.countryCode,
        regions = this.regions,
        types = this.types
    )
}