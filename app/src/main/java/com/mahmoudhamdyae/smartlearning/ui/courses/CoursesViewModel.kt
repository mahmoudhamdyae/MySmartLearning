package com.mahmoudhamdyae.smartlearning.ui.courses

import androidx.lifecycle.*
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import kotlinx.coroutines.launch

class CoursesViewModel(private val repository: FirebaseRepository) : BaseViewModel() {

    val courseName = MutableLiveData<String>()

    private var _courses = MutableLiveData<MutableList<Course?>>()
    val courses: LiveData<MutableList<Course?>>
        get() = _courses

    private var _user = MutableLiveData<User?>()
    val user: LiveData<User?>
        get() = _user

    fun getUserData() {
        try {
            viewModelScope.launch {
                _user = repository.getUserData()
            }
        } catch (_: Exception) {}
    }

    fun addCourse(course: Course) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.addCourse(course).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _status.value = STATUS.DONE
                } else {
                    _status.value = STATUS.ERROR
                    _error.value = task.exception?.message
                }
            }
        }
    }

    fun getListOfCourses() {
        try {
            viewModelScope.launch {
                _status.value = STATUS.LOADING
                _courses = repository.getCourses()
                _error.value = _courses.value.toString()
                _status.value = STATUS.DONE
            }
        } catch (_: Exception) {}
    }
}

@Suppress("UNCHECKED_CAST")
class CoursesViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (CoursesViewModel(repository) as T)
}