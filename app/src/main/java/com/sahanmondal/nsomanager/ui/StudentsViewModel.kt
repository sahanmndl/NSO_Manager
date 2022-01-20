package com.sahanmondal.nsomanager.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.sahanmondal.nsomanager.data.StudentDatabase
import com.sahanmondal.nsomanager.data.Student
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StudentsViewModel(app: Application) : AndroidViewModel(app) {

    private var allStudents: MutableLiveData<List<Student>> = MutableLiveData()

    init {
        getAllStudents()
    }

    fun getAllStudentsObservers(): MutableLiveData<List<Student>> {
        return allStudents
    }

    private fun getAllStudents() {
        GlobalScope.launch {
            val studentDao = StudentDatabase.getDatabase(getApplication())?.studentDao()
            val studentsList = studentDao?.getAllStudents()
            allStudents.postValue(studentsList)
        }
    }

    fun insertStudentInfo(student: Student) {
        GlobalScope.launch {
            val studentDao = StudentDatabase.getDatabase(getApplication())?.studentDao()
            studentDao?.insertStudent(student)
        }
        getAllStudents()
    }

    fun updateStudentInfo(student: Student) {
        GlobalScope.launch {
            val studentDao = StudentDatabase.getDatabase(getApplication())?.studentDao()
            studentDao?.updateStudent(student)
        }
        getAllStudents()
    }

    fun deleteStudentInfo(student: Student) {
        val studentDao = StudentDatabase.getDatabase(getApplication())?.studentDao()
        studentDao?.deleteStudent(student)

        getAllStudents()
    }
}