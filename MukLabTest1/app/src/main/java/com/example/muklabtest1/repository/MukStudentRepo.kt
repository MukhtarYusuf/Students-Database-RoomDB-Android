package com.example.muklabtest1.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.muklabtest1.db.MukLabTest1Database
import com.example.muklabtest1.model.MukStudent

class MukStudentRepo(var context: Context) {

    // Variables
    private var mukDb = MukLabTest1Database.mukGetInstance(context)
    private var mukStudentDao = mukDb.mukStudentDao()

    // Methods
    fun mukGetAllLiveStudents(): LiveData<List<MukStudent>> {
        return mukStudentDao.mukLoadAll()
    }

    fun mukGetLiveStudent(mukStudentId: Long): LiveData<MukStudent> {
        return mukStudentDao.mukLoadLiveStudent(mukStudentId)
    }

    fun mukGetStudent(mukStudentId: Long): MukStudent {
        return mukStudentDao.mukLoadStudent(mukStudentId)
    }

    fun mukAddStudent(mukStudent: MukStudent): Long {
        val mukId = mukStudentDao.mukInsertStudent(mukStudent)
        mukStudent.mukId = mukId

        return mukId
    }

    fun mukUpdateStudent(mukStudent: MukStudent) {
        mukStudentDao.mukUpdateStudent(mukStudent)
    }

    fun mukDeleteStudent(mukStudent: MukStudent) {
        mukStudentDao.mukDeleteStudent(mukStudent)
    }

    fun mukCreateStudent(): MukStudent {
        return MukStudent()
    }
}