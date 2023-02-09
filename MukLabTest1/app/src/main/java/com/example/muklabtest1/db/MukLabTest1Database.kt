package com.example.muklabtest1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.muklabtest1.model.MukDateConverter
import com.example.muklabtest1.model.MukStudent

@Database(entities = [MukStudent::class], version = 1)
@TypeConverters(MukDateConverter::class)
abstract class MukLabTest1Database: RoomDatabase() {
    abstract fun mukStudentDao(): MukStudentDao

    companion object {
        private var mukInstance: MukLabTest1Database? = null

        fun mukGetInstance(context: Context): MukLabTest1Database {
            if (mukInstance == null) {
                mukInstance = Room.databaseBuilder(context.applicationContext,
                MukLabTest1Database::class.java,
                "MukLabTest1")
                    .fallbackToDestructiveMigration()
                    .build()
            }

            return mukInstance as MukLabTest1Database
        }
    }
}