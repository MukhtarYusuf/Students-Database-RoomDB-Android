package com.example.muklabtest1.model

import androidx.room.*
import java.util.*

@Entity
data class MukStudent(
    @PrimaryKey(autoGenerate = true) var mukId: Long? = null,
    var mukName: String = "",
    var mukAge: Int = 0,
    var mukTuition: Double = 0.0,
    var mukStartDate: Date = Date()
)