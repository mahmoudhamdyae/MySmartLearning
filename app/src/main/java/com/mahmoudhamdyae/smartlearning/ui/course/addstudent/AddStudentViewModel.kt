package com.mahmoudhamdyae.smartlearning.ui.course.addstudent

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import kotlinx.coroutines.launch

class AddStudentViewModel(
    private val repository: FirebaseRepository
) : BaseViewModel() {

    private val _students = MutableLiveData<List<User>>()
    val students: LiveData<List<User>>
        get() = _students

    init {
        getListOfStudents()
    }

    fun addStudentToCourse(user: User, courseId: String) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.addStudentToCourse(user, courseId).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onCompleteListener(repository.addCourseToStudent(user, courseId))
                    addNoOfStudents(courseId)
                } else {
                    _status.value = STATUS.ERROR
                    _error.value = task.exception?.message
                }
            }
        }
    }

    private fun addNoOfStudents(courseId: String) {
        _status.value = STATUS.LOADING
        repository.getNoOfStudentsInCourse(courseId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val noOfStudents = snapshot.getValue(Int::class.java)!! + 1
                repository.updateNoOfStudents(courseId, noOfStudents)
                _status.value = STATUS.DONE
            }

            override fun onCancelled(error: DatabaseError) {
                _status.value = STATUS.ERROR
            }
        })
    }

    private fun getListOfStudents() {
        try {
            viewModelScope.launch {
                _status.value = STATUS.LOADING
                repository.getAllStudents().addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val studentsList: MutableList<User> = mutableListOf()
                        for (student in snapshot.children) {
                            val studentItem = student.getValue(User::class.java)
                            if (!studentItem!!.teacher) {
                                studentsList.add(studentItem)
                            }
                        }
                        _students.value = studentsList
                        _status.value = STATUS.DONE
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
        } catch (e: Exception) {
            _error.value = e.message
            _status.value = STATUS.ERROR
        }
    }
}

@Suppress("UNCHECKED_CAST")
class AddStudentViewModelFactory (
    private val repository: FirebaseRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (AddStudentViewModel(repository) as T)
}