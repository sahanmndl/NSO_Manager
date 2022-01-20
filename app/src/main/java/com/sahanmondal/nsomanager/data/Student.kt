package com.sahanmondal.nsomanager.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "students")
data class Student(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val roll: Int,
    val credits: Int,
    val hours: Int
) : Serializable
