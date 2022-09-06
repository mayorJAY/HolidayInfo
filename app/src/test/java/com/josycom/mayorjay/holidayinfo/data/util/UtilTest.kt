package com.josycom.mayorjay.holidayinfo.data.util

import com.josycom.mayorjay.holidayinfo.util.getFormattedDate
import com.josycom.mayorjay.holidayinfo.util.getJoinedString
import junit.framework.TestCase
import java.text.SimpleDateFormat

class UtilTest : TestCase() {

    fun `test getFormattedDate change a given date from one format to another`() {
        val fromFormat = SimpleDateFormat("yyyy-MM-dd")
        val toFormat = SimpleDateFormat("dd-MMM-yyyy")
        val result = "1921-02-23".getFormattedDate(fromFormat, toFormat)
        assertEquals("23-Feb-1921", result)
    }

    fun `test getFormattedDate change a given date from a different format to another`() {
        val fromFormat = SimpleDateFormat("dd MMM yyyy")
        val toFormat = SimpleDateFormat("dd-MM-yyyy")
        val result = "01 OCT 1969".getFormattedDate(fromFormat, toFormat)
        assertEquals("01-10-1969", result)
    }

    fun `test getJoinedString pass_empty_list empty_String_returned`() {
        val list = listOf<String>()
        val result = list.getJoinedString()
        assertTrue(result.isBlank())
    }

    fun `test getJoinedString pass_valid_list concatenated_String_returned`() {
        val list = listOf("Java", "Kotlin", "Android", "JetPack")
        val result = list.getJoinedString()
        assertEquals("Java, Kotlin, Android, JetPack", result)
    }
}