package com.example.muklabtest1.ui

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.example.muklabtest1.R
import com.example.muklabtest1.utils.MukDateUtils
import com.example.muklabtest1.viewmodel.MukStudentDetailsViewModel
import com.example.muklabtest1.viewmodel.MukStudentsViewModel
import kotlinx.android.synthetic.main.activity_muk_student_details.*
import kotlinx.android.synthetic.main.activity_muk_student_list.*
import kotlinx.android.synthetic.main.activity_muk_student_list.toolbar

class MukStudentDetailsActivity : AppCompatActivity() {

    // Variables
    private var mukIsAdd = true
    private lateinit var mukStudentDetailsViewModel: MukStudentDetailsViewModel
    private var mukStudentDetailsView: MukStudentDetailsViewModel.MukStudentDetailsView? = null
    private var mukDeleteItem: MenuItem? = null

    // Activity Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muk_student_details)

        mukSetupToolbar()
        mukSetupViewModel()
        mukSetupStudentObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_student_details, menu)
        mukDeleteItem = menu?.findItem(R.id.mukDeleteItem)
        mukUpdateUI()

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mukSaveItem -> {
                mukSaveStudent()
                return true
            }
            R.id.mukDeleteItem -> {
                mukDeleteStudent()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // Utilities
    private fun mukSetupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun mukSetupViewModel() {
        mukStudentDetailsViewModel = ViewModelProvider(this).get(MukStudentDetailsViewModel::class.java)
    }

    private fun mukSetupStudentObserver() {
        val mukId = intent.getLongExtra(MukStudentListActivity.MUK_STUDENT_ID, 0)
        mukStudentDetailsViewModel.mukGetStudent(mukId)?.observe(this) {
            it?.let {
                mukIsAdd = false
                mukStudentDetailsView = it
                mukUpdateUI()
            }
        }
    }

    private fun mukSaveStudent() {
        if (mukStudentDetailsView == null && mukIsAdd) { // Don't need to check both
            mukStudentDetailsView = mukStudentDetailsViewModel.mukCreateStudentDetailsView()
        }

        mukStudentDetailsView?.let {
            if (mukValidateFields()) {
                it.mukName = mukNameEditText.text.toString()
                it.mukAge = mukAgeEditText.text.toString()
                it.mukTuition = mukTuitionEditText.text.toString()
                it.mukDate = mukStartDateEditText.text.toString()

                var mukMessage = ""
                if (mukIsAdd) {
                    mukStudentDetailsViewModel.mukAddStudent(it)
                    mukMessage = "Student Added"
                } else {
                    mukStudentDetailsViewModel.mukUpdateStudent(it)
                    mukMessage = "Student Updated"
                }

                Toast.makeText(this, mukMessage, Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun mukDeleteStudent() {
        mukStudentDetailsView?.let {
            mukStudentDetailsViewModel.mukDeleteStudent(it)
            Toast.makeText(this, "Student Deleted", Toast.LENGTH_LONG).show()

            finish()
        }
    }

    private fun mukUpdateUI() {
        if (mukIsAdd) {
            title = "Add Student"
            mukDeleteItem?.isVisible = false
        } else {
            title = "Edit Student"
        }

        mukStudentDetailsView?.let {
            mukNameEditText.setText(it.mukName)
            mukAgeEditText.setText(it.mukAge)
            mukTuitionEditText.setText(it.mukTuition)
            mukStartDateEditText.setText(it.mukDate)
        }
    }

    private fun mukValidateFields(): Boolean {
        var mukIsValid = true
        var mukMessage = ""

        val mukValidName = mukNameEditText.text.toString()
        if (mukValidName.isEmpty()) {
            mukIsValid = false
            mukMessage = "Please Enter a Valid Name \n"
        }

        try {
            val mukAge = mukAgeEditText.text.toString().toInt()
        } catch (e: Exception) {
            mukIsValid = false
            mukMessage += "Please Enter a Valid Age \n"
        }

        try {
            val mukTuition = mukTuitionEditText.text.toString().toDouble()
        } catch (e: Exception) {
            mukIsValid = false
            mukMessage += "Please Enter a Valid Tuition \n"
        }

        try {
            val mukDate = MukDateUtils.mukStringToDate(mukStartDateEditText.text.toString())
        } catch (e: Exception) {
            mukIsValid = false
            mukMessage += "Please Enter a Valid Date \n"
        }

        if (!mukIsValid) {
            mukDisplayAlert(mukMessage)
        }

        return mukIsValid
    }

    private fun mukDisplayAlert(mukMessage: String) {
        AlertDialog.Builder(this)
            .setTitle("Invalid Input")
            .setMessage(mukMessage)
            .setPositiveButton("Ok", null)
            .create()
            .show()
    }
}