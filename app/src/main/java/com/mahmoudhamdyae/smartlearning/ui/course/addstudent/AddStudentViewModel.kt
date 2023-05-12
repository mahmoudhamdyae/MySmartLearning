package com.mahmoudhamdyae.smartlearning.ui.course.addstudent

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
class AddStudentViewModel @Inject constructor(
    private val repository: FirebaseRepository
) : BaseViewModel() {

    private val _students = MutableLiveData<List<User>>()
    val students: LiveData<List<User>>
        get() = _students

    private var _navigate = MutableLiveData(false)
    val navigate: LiveData<Boolean>
        get() = _navigate

    fun addStudentToCourse(user: User, course: Course) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.addStudentToCourse(user, course.id) { error1 ->
                if (error1 == null) {
                    repository.addCourseToUser(user.id, course) { error2 ->
                        if (error2 == null) {
                            _status.value = STATUS.DONE
                        } else {
                            _status.value = STATUS.ERROR
                            _error.value = error2.message.toString()
                        }
                    }
                    course.studentsNo += 1
                    addNoOfStudents(course)
                    navigate()
                } else {
                    _status.value = STATUS.ERROR
                    _error.value = error1.message.toString()
                }
            }
        }
    }

    private fun addNoOfStudents(course: Course) {
        repository.updateNoOfStudents(course.id, course.studentsNo, course.teacher!!)
    }

    fun getListOfStudents(courseId: String) {
        try {
            viewModelScope.launch {
                _status.value = STATUS.LOADING
                repository.getAllUsers({ users ->
                    val studentsList: MutableList<User> = mutableListOf()
                    for (userItem in users) {
                        if (!userItem.teacher) {
                            repository.getStudentsOfCourse(courseId, { students ->
                                var isStudentHere = false
                                for (studentItem in students) {
                                    if (userItem.id == studentItem.id) {
                                        isStudentHere = true
                                        break
                                    }
                                }
                                if (!isStudentHere) {
                                    studentsList.add(userItem)
                                }
                                _students.value = studentsList
                                _status.value = STATUS.DONE
                            }, { error ->
                                if (error != null) {
                                    _status.value = STATUS.ERROR
                                    _error.value = error.message
                                }
                            })
                        }
                    }
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

    private fun navigate() {
        _navigate.value = true
    }

    fun finishNavigate() {
        _navigate.value = false
    }
}