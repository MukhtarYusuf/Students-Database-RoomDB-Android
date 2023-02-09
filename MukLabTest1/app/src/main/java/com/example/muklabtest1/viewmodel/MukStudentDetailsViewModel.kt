package com.example.muklabtest1.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.muklabtest1.model.MukStudent
import com.example.muklabtest1.repository.MukStudentRepo
import com.example.muklabtest1.utils.MukDateUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MukStudentDetailsViewModel(application: Application): AndroidViewModel(application) {

    // Variables
    private var mukStudentRepo = MukStudentRepo(getApplication())
    private var mukStudentDetailsView: LiveData<MukStudentDetailsView>? = null

    // Methods
    fun mukGetStudent(mukId: Long): LiveData<MukStudentDetailsView>? {
        if (mukStudentDetailsView == null) {
            mukMapStudentToStudentDetailsView(mukId)
        }

        return mukStudentDetailsView
    }

    fun mukAddStudent(mukStudentDetailsView: MukStudentDetailsView) {
        val mukStudent = mukStudentRepo.mukCreateStudent()
        mukStudent.mukId = mukStudentDetailsView.mukId // No need
        mukStudent.mukName = mukStudentDetailsView.mukName
        mukStudent.mukAge = mukStudentDetailsView.mukAge.toInt()
        mukStudent.mukTuition = mukStudentDetailsView.mukTuition.toDouble()
        mukStudent.mukStartDate = MukDateUtils.mukStringToDate(mukStudentDetailsView.mukDate)

        GlobalScope.launch {
            mukStudentRepo.mukAddStudent(mukStudent)
        }
    }

    fun mukUpdateStudent(mukStudentDetailsView: MukStudentDetailsView) {
        GlobalScope.launch {
            val mukStudent = mukStudentDetailsViewToStudent(mukStudentDetailsView)
            mukStudent?.let {
                mukStudentRepo.mukUpdateStudent(it)
            }
        }
    }

    fun mukDeleteStudent(mukStudentDetailsView: MukStudentDetailsView) {
        GlobalScope.launch {
            val mukStudent = mukStudentDetailsViewToStudent(mukStudentDetailsView)
            mukStudent?.let {
                mukStudentRepo.mukDeleteStudent(it)
            }
        }
    }

    fun mukCreateStudentDetailsView(): MukStudentDetailsView {
        return MukStudentDetailsView()
    }

    // Utilities
    private fun mukStudentToStudentDetailsView(mukStudent: MukStudent): MukStudentDetailsView {
        return MukStudentDetailsView(
            mukStudent.mukId,
            "${mukStudent.mukName}",
            "${mukStudent.mukAge}",
            "${mukStudent.mukTuition}",
            MukDateUtils.mukDateToString(mukStudent.mukStartDate)
        )
    }

    private fun mukStudentDetailsViewToStudent(mukStudentDetailsView: MukStudentDetailsView): MukStudent? {
        val mukStudent = mukStudentDetailsView.mukId?.let {
            mukStudentRepo.mukGetStudent(it)
        }

        mukStudent?.let {
            it.mukId = mukStudentDetailsView.mukId
            it.mukName = mukStudentDetailsView.mukName
            it.mukAge = mukStudentDetailsView.mukAge.toInt()
            it.mukTuition = mukStudentDetailsView.mukTuition.toDouble()
            it.mukStartDate = MukDateUtils.mukStringToDate(mukStudentDetailsView.mukDate)
        }

        return mukStudent
    }

    private fun mukMapStudentToStudentDetailsView(mukId: Long) {
        val mukStudent = mukStudentRepo.mukGetLiveStudent(mukId)
        mukStudentDetailsView = Transformations.map(mukStudent) {
            it?.let {
                mukStudentToStudentDetailsView(it)
            }
        }
    }

    data class MukStudentDetailsView(
        var mukId: Long? = null,
        var mukName: String = "",
        var mukAge: String = "",
        var mukTuition: String = "",
        var mukDate: String = ""
    )
}