package com.sahanmondal.nsomanager.data

import androidx.room.*

@Dao
interface StudentDao {

    @Query("SELECT * FROM students")
    fun getAllStudents(): List<Student>

    @Insert
    fun insertStudent(vararg student: Student)

    @Update
    fun updateStudent(vararg student: Student)

    @Delete
    fun deleteStudent(student: Student)
}