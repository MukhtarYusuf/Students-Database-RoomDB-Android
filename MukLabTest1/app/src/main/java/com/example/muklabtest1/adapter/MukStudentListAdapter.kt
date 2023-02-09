package com.example.muklabtest1.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muklabtest1.R
import com.example.muklabtest1.model.MukStudent
import com.example.muklabtest1.viewmodel.MukStudentsViewModel

class MukStudentListAdapter(
    private var mukStudentViews: List<MukStudentsViewModel.MukStudentView>?,
    private var mukStudentListAdapterListener: MukStudentListAdapterListener
): RecyclerView.Adapter<MukStudentListAdapter.MukViewHolder>() {

    // Interfaces
    interface MukStudentListAdapterListener {
        fun mukOnStudentClicked(mukStudentView: MukStudentsViewModel.MukStudentView)
    }

    // View Holder
    class MukViewHolder(view: View,
        private val mukStudentListAdapterListener: MukStudentListAdapterListener): RecyclerView.ViewHolder(view) {

        val mukNameTextView: TextView = view.findViewById(R.id.nameTextView)
        val mukAgeTextView: TextView = view.findViewById(R.id.ageTextView)
        val mukTuitionTextView: TextView = view.findViewById(R.id.tuitionTextView)
        val mukDateTextView: TextView = view.findViewById(R.id.startDateTextView)

        init {
            view.setOnClickListener {
                val mukStudentView = itemView.tag as? MukStudentsViewModel.MukStudentView
                mukStudentView?.let {
                    mukStudentListAdapterListener.mukOnStudentClicked(it)
                }
            }
        }
    }

    // Adapter Methods
    override fun getItemCount(): Int {
        return mukStudentViews?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MukViewHolder {
        val mukView = LayoutInflater.from(parent.context).inflate(R.layout.muk_student_item, parent, false)

        return MukViewHolder(mukView, mukStudentListAdapterListener)
    }

    override fun onBindViewHolder(holder: MukViewHolder, position: Int) {
        val mukStudentViews = mukStudentViews ?: return

        val mukStudentView = mukStudentViews[position]
        holder.itemView.tag = mukStudentView

        holder.mukNameTextView.text = mukStudentView.mukName
        holder.mukAgeTextView.text = mukStudentView.mukAge
        holder.mukTuitionTextView.text = mukStudentView.mukTuition
        holder.mukDateTextView.text = mukStudentView.mukStartDate
    }

    // Methods
    fun mukSetStudentViews(mukStudentViewsData: List<MukStudentsViewModel.MukStudentView>) {
        mukStudentViews = mukStudentViewsData
        notifyDataSetChanged()
    }
}