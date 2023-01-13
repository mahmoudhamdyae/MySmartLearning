package com.mahmoudhamdyae.smartlearning.ui.courses

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.utils.Constants

class CoursesViewModel: ViewModel() {

    val courseName = MutableLiveData<String>()

    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>>
        get() = _courses

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var databaseReference: DatabaseReference =
        FirebaseDatabase.getInstance().reference.child(Constants.USERS).child(mAuth.currentUser!!.uid)

    init {
        // todo delete this
        val c = mutableListOf<Course>()
        c.add(Course("name1", "year1", "teacherName1"))
        c.add(Course("name2", "year2", "teacherName2"))
        c.add(Course("name3", "year3", "teacherName3"))

        _courses.value = c

        getUserData()
    }

    private fun getUserData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                _user.value = dataSnapshot.getValue(User::class.java)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w("LogInViewModel", "loadPost:onCancelled", databaseError.toException())
            }
        })
    }
}