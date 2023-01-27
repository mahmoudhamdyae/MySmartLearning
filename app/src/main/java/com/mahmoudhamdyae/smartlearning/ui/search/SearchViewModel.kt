package com.mahmoudhamdyae.smartlearning.ui.search

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import kotlinx.coroutines.launch
import java.util.*

class SearchViewModel(
    private val repository: FirebaseRepository
) : BaseViewModel() {

    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>>
        get() = _courses

    init {
        getListOfCourses()
    }

    private fun getListOfCourses() {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.getAllCourses().addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val coursesList: MutableList<Course> = mutableListOf()
                    for (course in snapshot.children) {
                        val courseItem = course.getValue(Course::class.java)
                        repository.getUserCourses().addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                var isEnteredCourse = false
                                for (userCourse in dataSnapshot.children) {
                                    val userCourseItem = userCourse.getValue(Course::class.java)
                                    if (courseItem!!.id == userCourseItem!!.id) {
                                        isEnteredCourse = true
                                        break
                                    }
                                }
                                if (!isEnteredCourse) {
                                    coursesList.add(courseItem!!)
                                }
                                _courses.value = coursesList
                                _status.value = STATUS.DONE
                            }

                            override fun onCancelled(error: DatabaseError) {
                                _status.value = STATUS.ERROR
                            }
                        })
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(
                        "getStudents:Cancelled",
                        "loadStudents:onCancelled",
                        error.toException()
                    )
                    _status.value = STATUS.ERROR
                }
            })
        }
    }

    fun addCourse(course: Course) {
        viewModelScope.launch {
            onCompleteListener(repository.addCourseToUser(course))
            addNoOfStudents(course.id)
        }
    }

    private fun addNoOfStudents(courseId: String) {
        _status.value = STATUS.LOADING
        repository.getNoOfStudentsInCourse(courseId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val noOfStudents = snapshot.getValue(Int::class.java)!! + 1
                onCompleteListener(repository.updateNoOfStudents(courseId, noOfStudents))
                _status.value = STATUS.DONE
            }

            override fun onCancelled(error: DatabaseError) {
                _status.value = STATUS.ERROR
            }
        })
    }
}

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (SearchViewModel(repository) as T)
}