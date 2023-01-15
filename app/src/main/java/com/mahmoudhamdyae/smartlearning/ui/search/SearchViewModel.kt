package com.mahmoudhamdyae.smartlearning.ui.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Course

class SearchViewModel(application: Application): BaseViewModel(application) {

    private val _courses = MutableLiveData<MutableList<Course>>(mutableListOf())
    val courses: LiveData<MutableList<Course>>
        get() = _courses

    init {
        // todo delete this
        _courses.value?.add(Course("name1", "year1", "teacherName1"))
        _courses.value?.add(Course("name2", "year2", "teacherName2"))
        _courses.value?.add(Course("name3", "year3", "teacherName3"))
    }
}