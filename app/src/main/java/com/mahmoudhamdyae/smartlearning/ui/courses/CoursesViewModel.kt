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
            this@CoursesViewModel._status.value = STATUS.LOADING
            repository.addCourseToCourses(course).addOnCompleteListener { courseTask ->
                if (courseTask.isSuccessful) {
                    onCompleteListener(repository.addCourseToUser(course))
                } else {
                    this@CoursesViewModel._status.value = STATUS.ERROR
                    _error.value = courseTask.exception?.message
                }
            }
        }
    }

    fun getListOfCourses() {
        try {
            viewModelScope.launch {
                _status.value = STATUS.LOADING
                repository.getUserCourses().addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val coursesList : MutableList<Course> = mutableListOf()
                        for (course in dataSnapshot.children) {
                            val courseItem = course.getValue(Course::class.java)
                            coursesList.add(courseItem!!)
                        }
                        _courses.value = coursesList
                        _status.value = STATUS.DONE
                    }
                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.w("getCourses:onCancelled", "loadCourses:onCancelled", databaseError.toException())
                        _status.value = STATUS.ERROR
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
            repository.delCourseFromCourses(courseId).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    delCourseFromStudents(courseId)
                    delMaterials(courseId)
                } else {
                    _status.value = STATUS.ERROR
                    _error.value = task.exception?.message
                }
            }
        }
    }

    private fun delCourseFromStudents(courseId: String) {
        repository.delCourseFromStudents(courseId)
    }

    fun delCourseFromUser(courseId: String) {
        viewModelScope.launch {
            onCompleteListener(repository.delCourseFromUser(courseId))
        }
    }

    private fun delMaterials(courseId: String) {
        repository.delMaterialsStorage(courseId)
    }

    fun decreaseNoOfStudents(courseId: String) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.getNoOfStudentsInCourse(courseId).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val noOfStudents = snapshot.getValue(Int::class.java)!! - 1
                    onCompleteListener(repository.updateNoOfStudents(courseId, noOfStudents))
                    _status.value = STATUS.DONE
                }

                override fun onCancelled(error: DatabaseError) {
                    _status.value = STATUS.ERROR
                }
            })
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