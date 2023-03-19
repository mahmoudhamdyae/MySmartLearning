package com.mahmoudhamdyae.smartlearning.ui.courses

import androidx.lifecycle.*
import com.google.firebase.database.*
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import kotlinx.coroutines.launch

class CoursesViewModel(private val repository: FirebaseRepository) : BaseViewModel() {

    val courseName = MutableLiveData<String>()

    private val _courses = MutableLiveData<List<Course>>()
    val courses: LiveData<List<Course>>
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
            repository.addCourseToCourses(course) { error1 ->
                if (error1 == null) {
                    repository.addCourseToUser(repository.getUid(), course) { error2 ->
                        if (error2 == null) {
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

    fun getListOfCourses() {
        try {
            viewModelScope.launch {
                _status.value = STATUS.LOADING
                repository.getUserCourses ({ courses ->
                    _courses.value = courses
                    _status.value = STATUS.DONE
                }, { error ->
                    if (error != null) {
                        _status.value = STATUS.ERROR
                        _error.value = error.message
                    }
                })
            }
        } catch (e: Exception) {
            _error.value = e.message
            _status.value = STATUS.ERROR
        }
    }

    fun delCourse(courseId: String) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            delCourseFromStudents(courseId)
            repository.delCourseFromCourses(courseId) { error ->
                if (error == null) {
                    delMaterials(courseId)
                    _status.value = STATUS.DONE
                } else {
                    _status.value = STATUS.ERROR
                    _error.value = error.message.toString()
                }
            }
        }
    }

    private fun delCourseFromStudents(courseId: String) {
        repository.delCourseFromStudents(courseId) { error ->
            if (error != null) {
                _error.value = error.message.toString()
            }
        }
    }

    fun delCourseFromUser(courseId: String) {
        viewModelScope.launch {
            repository.delCourseFromUser(courseId) { error ->
                if (error != null) {
                    _error.value = error.message.toString()
                }
            }
        }
    }

    fun delStudentFromCourse(courseId: String) {
        viewModelScope.launch {
            repository.delStudentFromCourse(repository.getUid(), courseId) { error ->
                if (error == null) {
                    _status.value = STATUS.DONE
                } else {
                    _status.value = STATUS.ERROR
                    _error.value = error.message.toString()
                }
            }
        }
    }

    private fun delMaterials(courseId: String) {
        repository.delMaterialsStorage(courseId) {}
    }

    fun decreaseNoOfStudents(course: Course) {
        viewModelScope.launch {
            repository.updateNoOfStudents(course.id, course.studentsNo - 1, course.teacher!!)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class CoursesViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (CoursesViewModel(repository) as T)
}