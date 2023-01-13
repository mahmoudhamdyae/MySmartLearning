package com.mahmoudhamdyae.smartlearning.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Course

class SearchViewModel: ViewModel() {

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