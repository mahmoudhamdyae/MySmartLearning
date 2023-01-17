package com.mahmoudhamdyae.smartlearning.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
        repository.getAllCourses()

        _courses.value = mutableListOf(Course("name1", "year1", "teacherName1"))
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