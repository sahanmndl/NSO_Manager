package com.sahanmondal.nsomanager.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import com.sahanmondal.nsomanager.R
import com.sahanmondal.nsomanager.UpdateStudentActivity
import com.sahanmondal.nsomanager.data.Student

class StudentAdapter(private var students: List<Student>) :
    RecyclerView.Adapter<StudentAdapter.StudentHolder>() {

    class StudentHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: MaterialTextView = view.findViewById(R.id.tvName)
        val roll: MaterialTextView = view.findViewById(R.id.tvRollNumber)
        val credits: MaterialTextView = view.findViewById(R.id.tvCredits)
        val hours: MaterialTextView = view.findViewById(R.id.tvHours)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_student, parent, false)
        return StudentHolder(view)
    }

    override fun onBindViewHolder(holder: StudentHolder, position: Int) {
        val student = students[position]
        val context = holder.roll.context

        holder.name.text = student.name
        holder.roll.text = student.roll.toString()
        holder.credits.text = student.credits.toString()
        holder.hours.text = student.hours.toString()

        holder.itemView.setOnClickListener {
            val intent = Intent(context, UpdateStudentActivity::class.java)
            intent.putExtra("STUDENT_DATA", student)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return students.size
    }

    fun setListData(students: List<Student>) {
        this.students = students
        notifyDataSetChanged()
    }
}