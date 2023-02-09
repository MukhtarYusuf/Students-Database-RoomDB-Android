package com.example.muklabtest1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.muklabtest1.model.MukStudent
import com.example.muklabtest1.repository.MukStudentRepo
import com.example.muklabtest1.utils.MukDateUtils

class MukStudentsViewModel(application: Application): AndroidViewModel(application) {

    // Variables
    private var mukStudentRepo = MukStudentRepo(getApplication())
    private var mukStudentViews: LiveData<List<MukStudentView>>? = null

    // Methods
    fun mukGetStudentViews(): LiveData<List<MukStudentView>>? {
        if (mukStudentViews == null) {
            mukMapStudentsToStudentViews()
        }

        return mukStudentViews
    }

    // Utilities
    private fun mukStudentToStudentView(mukStudent: MukStudent): MukStudentView {
        return MukStudentView(
            mukStudent.mukId,
            mukStudent.mukName,
            "${mukStudent.mukAge}",
            "${mukStudent.mukTuition}",
            MukDateUtils.mukDateToString(mukStudent.mukStartDate)
        )
    }

    private fun mukMapStudentsToStudentViews() {
        mukStudentViews = Transformations.map(mukStudentRepo.mukGetAllLiveStudents()) { mukRepoStudents ->
            mukRepoStudents.map { mukStudent ->
                mukStudentToStudentView(mukStudent)
            }
        }
    }

    data class MukStudentView(
        var mukId: Long? = null,
        var mukName: String = "",
        var mukAge: String = "",
        var mukTuition: String = "",
        var mukStartDate: String = ""
    )
}