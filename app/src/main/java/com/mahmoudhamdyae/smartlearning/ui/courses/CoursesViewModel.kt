package com.mahmoudhamdyae.smartlearning.ui.courses

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Course

class CoursesViewModel(application: Application): BaseViewModel(application) {

    val courseName = MutableLiveData<String>()

    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>>
        get() = _courses

    init {
        // todo delete this
        val c = mutableListOf<Course>()
        c.add(Course("name1", "year1", "teacherName1"))
        c.add(Course("name2", "year2", "teacherName2"))
        c.add(Course("name3", "year3", "teacherName3"))

        _courses.value = c

        getUserData()
    }
}