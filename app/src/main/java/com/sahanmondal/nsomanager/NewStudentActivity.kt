package com.sahanmondal.nsomanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import com.sahanmondal.nsomanager.data.Student
import com.sahanmondal.nsomanager.ui.StudentsViewModel
import kotlinx.android.synthetic.main.activity_new_student.*
import kotlin.math.abs
import kotlin.math.log10

class NewStudentActivity : AppCompatActivity() {

    private lateinit var viewModel: StudentsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_student)

        viewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)

        supportActionBar?.title = "New Student"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        etName.addTextChangedListener {
            if (it!!.count() > 0) {
                tilName.error = null
            }
        }

        etRollNumber.addTextChangedListener {
            if (it!!.count() > 0) {
                tilRollNumber.error = null
            }
        }

        etCredits.addTextChangedListener {
            if (it!!.count() > 0) {
                tilCredits.error = null
            }
        }

        etHours.addTextChangedListener {
            if (it!!.count() > 0) {
                tilHours.error = null
            }
        }

        addNewStudent()
    }

    private fun addNewStudent() {
        btnAddStudent.setOnClickListener {
            val name: String = etName.text.toString()
            val roll: Int? = etRollNumber.text.toString().toIntOrNull()
            val credits: Int? = etCredits.text.toString().toIntOrNull()
            val hours: Int? = etHours.text.toString().toIntOrNull()

            when {
                name.isEmpty() -> {
                    tilName.error = "Please enter a name"
                }
                roll == null -> {
                    tilRollNumber.error = "Please enter ID number"
                }
                roll < 0 -> {
                    tilRollNumber.error = "Please enter valid ID number"
                }
                roll.length() != 8 -> {
                    tilRollNumber.error = "Please enter valid ID number"
                }
                credits == null -> {
                    tilCredits.error = "Please enter the numbers of credits registered"
                }
                credits < 0 || credits > 6 -> {
                    tilCredits.error = "Credits must be within 1 - 6"
                }
                hours == null -> {
                    tilHours.error = "Please enter valid hours"
                }
                hours < 0 || hours > 120 -> {
                    tilHours.error = "Please enter valid hours"
                }
                else -> {
                    val student = Student(0, name, roll, credits, hours)
                    viewModel.insertStudentInfo(student)

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