package com.mahmoudhamdyae.smartlearning.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: FirebaseRepository
): BaseViewModel() {

    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>>
        get() = _courses

    init {
        getListOfCourses()
    }

    private fun getListOfCourses() {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.getAllCourses({ courses ->
                _courses.value = courses
                _status.value = STATUS.DONE
            }, { error ->
                if (error != null) {
                    _status.value = STATUS.ERROR
                    _error.value = error.message
                }
            })
        }
    }

    fun addCourse(course: Course, user: User) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.addCourseToUser(user.id, course) { error1 ->
                if (error1 == null) {
                    repository.addStudentToCourse(user, course.id) { error2 ->
                        if (error2 == null) {
                            addNoOfStudents(course)
                            _status.value = STATUS.DONE
                        } else {
                            _status.value = STATUS.ERROR
                            _error.value = error2.message.toString()
                        }
                    }
                } else {
                    _status.value = STATUS.ERROR
                    _error.value = error1.message.toString()
                }
            }
        }
    }

    private fun addNoOfStudents(course: Course) {
        repository.updateNoOfStudents(course.id, course.studentsNo + 1, course.teacher!!)
    }
}