package com.sahanmondal.nsomanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.sahanmondal.nsomanager.data.Student
import com.sahanmondal.nsomanager.ui.StudentsViewModel
import kotlinx.android.synthetic.main.activity_update_student.*
import kotlin.math.abs
import kotlin.math.log10

class UpdateStudentActivity : AppCompatActivity() {

    private lateinit var student: Student
    private lateinit var viewModel: StudentsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_student)

        student = intent.getSerializableExtra("STUDENT_DATA") as Student
        viewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)

        supportActionBar?.title = "Update Student"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        etUpName.setText(student.name)
        etUpRollNumber.setText(student.roll.toString())
        etUpCredits.setText(student.credits.toString())
        etUpHours.setText(student.hours.toString())

        etUpName.addTextChangedListener {
            if (it!!.count() > 0) {
                tilUpName.error = null
            }

            btnUpdateStudent.visibility = View.VISIBLE
        }

        etUpRollNumber.addTextChangedListener {
            if (it!!.count() > 0) {
                tilUpRollNumber.error = null
            }

            btnUpdateStudent.visibility = View.VISIBLE
        }

        etUpCredits.addTextChangedListener {
            if (it!!.count() > 0) {
                tilUpCredits.error = null
            }

            btnUpdateStudent.visibility = View.VISIBLE
        }

        etUpHours.addTextChangedListener {
            if (it!!.count() > 0) {
                tilUpHours.error = null
            }

            btnUpdateStudent.visibility = View.VISIBLE
        }

        updateStudentInfo()
    }

    private fun updateStudentInfo() {
        btnUpdateStudent.setOnClickListener {
            val name: String = etUpName.text.toString()
            val roll: Int? = etUpRollNumber.text.toString().toIntOrNull()
            val credits: Int? = etUpCredits.text.toString().toIntOrNull()
            val hours: Int? = etUpHours.text.toString().toIntOrNull()

            when {
                name.isEmpty() -> {
                    tilUpName.error = "Please enter a name"
                }
                roll == null -> {
                    tilUpRollNumber.error = "Please enter ID number"
                }
                roll < 0 -> {
                    tilUpRollNumber.error = "Please enter valid ID number"
                }
                roll.length() != 8 -> {
                    tilUpRollNumber.error = "Please enter valid ID number"
                }
                credits == null -> {
                    tilUpCredits.error = "Please enter the numbers of credits registered"
                }
                credits < 0 || credits > 6 -> {
                    tilUpCredits.error = "Credits must be within 1 - 6"
                }
                hours == null -> {
                    tilUpHours.error = "Please enter valid hours"
                }
                hours < 0 || hours > 120 -> {
                    tilUpHours.error = "Please enter valid hours"
                }
                else -> {
                    val student = Student(student.id, name, roll, credits, hours)
                    viewModel.updateStudentInfo(student)

                    finish()
                }
            }
        }
    }

    private fun Int.length() = when (this) {
        0 -> 1
        else -> log10(abs(toDouble())).toInt() + 1
    }
}