package com.mahmoudhamdyae.smartlearning.ui.courses

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository

class CoursesViewModel(application: Application): BaseViewModel(application) {

    val courseName = MutableLiveData<String>()

    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>>
        get() = _courses

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

    private val repository = FirebaseRepository()

    init {
        // todo delete this
        val c = mutableListOf<Course>()
        c.add(Course("name1", "year1", "teacherName1"))
        c.add(Course("name2", "year2", "teacherName2"))
        c.add(Course("name3", "year3", "teacherName3"))

        _courses.value = c

//        getUserData()
    }

    private fun getUserData() {
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                _user.value =  dataSnapshot.getValue(User::class.java)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("LogInViewModel", "loadPost:onCancelled", databaseError.toException())
            }
        }

        repository.getUserData(valueEventListener)
    }
}