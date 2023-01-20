package com.mahmoudhamdyae.smartlearning.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.ui.course.materials.MaterialsViewModel
import com.mahmoudhamdyae.smartlearning.utils.STATUS

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
        _downloadStatus.value = STATUS.LOADING
        repository.getAllCourses().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val coursesList: MutableList<Course> = mutableListOf()
                for (course in snapshot.children) {
                    val courseItem = course.getValue(Course::class.java)
                    // todo remove user's courses
                    coursesList.add(courseItem!!)
                }
                _courses.value = coursesList
                _downloadStatus.value = STATUS.DONE
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("getCourses:Cancelled", "loadCourses:onCancelled", error.toException())
                _downloadStatus.value = STATUS.ERROR
            }
        })

//        _courses.value = mutableListOf(Course("name1", "year1", "teacherName1"))
    }

    fun addCourse(course: Course) {
        _uploadStatus.value = STATUS.LOADING
        repository.addCourseToUser(course).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                _uploadStatus.value = STATUS.DONE
            } else {
                _uploadStatus.value = STATUS.ERROR
                _error.value = task.exception?.message
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (SearchViewModel(repository) as T)
}