package com.example.muklabtest1.model

import androidx.room.TypeConverter
import java.util.*

class MukDateConverter {
    @TypeConverter
    fun mukDateToLong(mukDate: Date): Long {
        return mukDate.time
    }

    @TypeConverter
    fun mukLongToDate(mukTime: Long): Date {
        return Date(mukTime)
    }
}