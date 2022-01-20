package com.sahanmondal.nsomanager.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {

        private var instance: StudentDatabase? = null

        fun getDatabase(context: Context): StudentDatabase? {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDatabase::class.java,
                    "students"
                ).build()
            }

            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}