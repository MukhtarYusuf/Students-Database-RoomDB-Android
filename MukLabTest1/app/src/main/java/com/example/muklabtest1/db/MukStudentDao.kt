package com.example.muklabtest1.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import com.example.muklabtest1.model.MukStudent

@Dao
interface MukStudentDao {
    @Query("SELECT * FROM MukStudent")
    fun mukLoadAll(): LiveData<List<MukStudent>>

    @Query("SELECT * FROM MukStudent WHERE mukId = :mukStudentId")
    fun mukLoadLiveStudent(mukStudentId: Long): LiveData<MukStudent>

    @Query("SELECT * FROM MukStudent WHERE mukId = :mukStudentId")
    fun mukLoadStudent(mukStudentId: Long): MukStudent

    @Insert(onConflict = IGNORE)
    fun mukInsertStudent(mukStudent: MukStudent): Long

    @Update(onConflict = REPLACE)
    fun mukUpdateStudent(mukStudent: MukStudent)

    @Delete
    fun mukDeleteStudent(mukStudent: MukStudent)
}