package com.mahmoudhamdyae.smartlearning.ui.courses

import android.util.Log
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
            _uploadStatus.value = STATUS.LOADING
            repository.addCourse(course).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _uploadStatus.value = STATUS.DONE
                } else {
                    _uploadStatus.value = STATUS.ERROR
                    _error.value = task.exception?.message
                }
            }
        }
    }

    fun getListOfCourses() {
        try {
            viewModelScope.launch {
                _downloadStatus.value = STATUS.LOADING
                repository.getCourses().addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val coursesList : MutableList<Course> = mutableListOf()
                        for (course in dataSnapshot.children) {
                            val courseItem = course.getValue(Course::class.java)
                            coursesList.add(courseItem!!)
                        }
                        _courses.value = coursesList
                        _downloadStatus.value = STATUS.DONE
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.w("getCourses:onCancelled", "loadCourses:onCancelled", databaseError.toException())
                        _downloadStatus.value = STATUS.ERROR
                    }

                })
            }
        } catch (e: Exception) {
            _error.value = e.message
            _downloadStatus.value = STATUS.ERROR
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