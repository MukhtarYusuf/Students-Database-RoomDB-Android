package com.example.muklabtest1.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object MukDateUtils {
    fun mukDateToString(mukDate: Date): String {
        val mukOutputFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault())
        return mukOutputFormat.format(mukDate)
    }

    fun mukStringToDate(mukDateString: String): Date {
        val mukInputFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault())
        return mukInputFormat.parse(mukDateString) as Date

//        val mukInputFormat = SimpleDateFormat("dd/MM/yyyy")
//        return mukInputFormat.parse(mukDateString)
    }
}