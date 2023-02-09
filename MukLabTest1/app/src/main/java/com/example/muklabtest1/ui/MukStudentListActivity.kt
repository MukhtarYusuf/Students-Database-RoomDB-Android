package com.example.muklabtest1.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muklabtest1.R
import com.example.muklabtest1.adapter.MukStudentListAdapter
import com.example.muklabtest1.model.MukStudent
import com.example.muklabtest1.viewmodel.MukStudentsViewModel
import kotlinx.android.synthetic.main.activity_muk_student_list.*

class MukStudentListActivity : AppCompatActivity(),
    MukStudentListAdapter.MukStudentListAdapterListener {

    // Variables
    private lateinit var mukStudentsViewModel: MukStudentsViewModel
    private lateinit var mukStudentListAdapter: MukStudentListAdapter


    // Activity Lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_muk_student_list)

        mukSetupToolbar()
        mukSetupViewModel()
        mukSetupAdapter()
        mukSetupStudentObserver()
        mukSetupListeners()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_students, menu)
//        return true
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mukAddItem) {
            mukGoToStudentDetails(null)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // MukStudentListAdapter.MukStudentListAdapterListener Methods
    override fun mukOnStudentClicked(mukStudentView: MukStudentsViewModel.MukStudentView) {
        mukStudentView.mukId?.let {
            mukGoToStudentDetails(it)
        }
    }

    // Utilities
    private fun mukSetupToolbar() {
        setSupportActionBar(toolbar)
    }

    private fun mukSetupViewModel() {
        mukStudentsViewModel = ViewModelProvider(this).get(MukStudentsViewModel::class.java)
    }

    private fun mukSetupAdapter() {
        val mukLayoutManager = LinearLayoutManager(this)
        mukStudentsRecyclerView.layoutManager = mukLayoutManager

        mukStudentListAdapter = MukStudentListAdapter(null, this)
        mukStudentsRecyclerView.adapter = mukStudentListAdapter
    }

    private fun mukSetupStudentObserver() {
        mukStudentsViewModel.mukGetStudentViews()?.observe(this) {
            it?.let {
                mukStudentListAdapter.mukSetStudentViews(it)
            }
        }
    }

    private fun mukSetupListeners() {
        mukFab.setOnClickListener {
            mukGoToStudentDetails(null)
        }
    }

    private fun mukGoToStudentDetails(mukId: Long?) {
        val mukIntent = Intent(this, MukStudentDetailsActivity::class.java)

        mukId?.let {
            mukIntent.putExtra(MUK_STUDENT_ID, it)
        }

        startActivity(mukIntent)
    }

    companion object{
        const val MUK_STUDENT_ID = "MUK_STUDENT_ID"
    }

}