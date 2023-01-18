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
        _uploadStatus.value = STATUS.LOADING
        repository.addStudentToCourse(user, courseId).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                repository.addCourseToStudent(user, courseId).addOnCompleteListener { secondTask ->
                    if (secondTask.isSuccessful) {
                        _uploadStatus.value = STATUS.DONE
                    } else {
                        _uploadStatus.value = STATUS.ERROR
                        _error.value = task.exception?.message
                    }
                }
            } else {
                _uploadStatus.value = STATUS.ERROR
                _error.value = task.exception?.message
            }
        }
    }

    private fun getListOfStudents() {
        try {
            viewModelScope.launch {
                _downloadStatus.value = STATUS.LOADING
            }
            repository.getAllStudents().addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val studentsList: MutableList<User> = mutableListOf()
                    for (student in snapshot.children) {
                        val materialItem = student.getValue(User::class.java)
                        studentsList.add(materialItem!!)
                    }
                    _students.value = studentsList
                    _downloadStatus.value = STATUS.DONE
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("getStudents:Cancelled", "loadStudents:onCancelled", error.toException())
                    _downloadStatus.value = STATUS.ERROR
                }
            })
        } catch (e: Exception) {
            _error.value = e.message
            _downloadStatus.value = STATUS.ERROR
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