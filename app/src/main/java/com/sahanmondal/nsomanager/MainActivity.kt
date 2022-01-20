package com.sahanmondal.nsomanager

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.sahanmondal.nsomanager.data.Student
import com.sahanmondal.nsomanager.data.StudentDatabase
import com.sahanmondal.nsomanager.ui.StudentAdapter
import com.sahanmondal.nsomanager.ui.StudentsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: StudentsViewModel
    private lateinit var studentList: List<Student>
    private lateinit var initialStudentList: List<Student>
    private lateinit var deletedStudent: Student
    private lateinit var studentAdapter: StudentAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var studentDB: StudentDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        studentList = arrayListOf()
        studentAdapter = StudentAdapter(studentList)
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.reverseLayout = true
        linearLayoutManager.stackFromEnd = true

        studentDB = Room.databaseBuilder(
            this,
            StudentDatabase::class.java,
            "students"
        ).build()

        rvStudents.apply {
            adapter = studentAdapter
            layoutManager = linearLayoutManager
        }

        viewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)

        fab.setOnClickListener {
            val intent = Intent(this, NewStudentActivity::class.java)
            startActivity(intent)
        }

        swipeToDelete()
        showEmptyMessage()
    }

    private fun fetchData() {
        GlobalScope.launch {
            studentList = studentDB.studentDao().getAllStudents()

            runOnUiThread {
                studentAdapter.setListData(studentList)
            }
        }
    }

    private fun deleteStudent(student: Student) {
        deletedStudent = student
        initialStudentList = studentList

        GlobalScope.launch {
            viewModel.deleteStudentInfo(student)
            studentList = studentList.filter { it.id != student.id }

            runOnUiThread {
                studentAdapter.setListData(studentList)
                showSnackBar()
            }
        }
    }

    private fun swipeToDelete() {
        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteStudent(studentList[viewHolder.adapterPosition])
            }
        }

        val swipeHelper = ItemTouchHelper(itemTouchHelper)
        swipeHelper.attachToRecyclerView(rvStudents)
    }

    private fun undoDelete() {
        viewModel.insertStudentInfo(deletedStudent)
        studentList = initialStudentList

        runOnUiThread {
            studentAdapter.setListData(studentList)
        }
    }

    private fun showSnackBar() {
        val view = findViewById<View>(R.id.coordinatorLayout)
        val snackBar = Snackbar.make(
            view,
            "Student info deleted!",
            Snackbar.LENGTH_LONG
        )

        snackBar.setAction("Undo") {
            undoDelete()
        }
            .setActionTextColor(ContextCompat.getColor(this, R.color.material_red))
            .show()
    }

    private fun showEmptyMessage() {
        studentAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                if (studentAdapter.itemCount == 0) {
                    rvStudents.visibility = View.GONE
                    tvTip.visibility = View.VISIBLE
                } else {
                    rvStudents.visibility = View.VISIBLE
                    tvTip.visibility = View.GONE
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }
}