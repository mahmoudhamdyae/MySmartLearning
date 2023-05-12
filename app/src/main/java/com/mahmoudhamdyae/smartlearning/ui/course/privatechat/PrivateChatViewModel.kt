package com.mahmoudhamdyae.smartlearning.ui.course.privatechat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.IsTeacher
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrivateChatViewModel @Inject constructor(
    private val repository: FirebaseRepository
): BaseViewModel() {

    private val _students = MutableLiveData<List<User>>()
    val students: LiveData<List<User>>
        get() = _students

    private val _teacher = MutableLiveData<User>()
    val teacher: LiveData<User>
        get() = _teacher

    fun getListOfStudents(courseId: String) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.getStudentsOfCourse(courseId, { students ->
                val studentsList: MutableList<User> = mutableListOf()
                if (_isTeacher.value != IsTeacher.TEACHER) {
                    studentsList.add(_teacher.value!!)
                }
                for (studentItem in students) {
                    if (repository.getUid() != studentItem.id) {
                        studentsList.add(studentItem)
                    }
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

    fun getTeacher(teacher: User) {
        _teacher.value = teacher
    }
}