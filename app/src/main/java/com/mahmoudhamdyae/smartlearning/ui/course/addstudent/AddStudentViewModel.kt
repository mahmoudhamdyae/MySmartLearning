package com.mahmoudhamdyae.smartlearning.ui.course.addstudent

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Course
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

    private var _navigate = MutableLiveData(false)
    val navigate: LiveData<Boolean>
        get() = _navigate

    fun addStudentToCourse(user: User, course: Course) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.addStudentToCourse(user, course.id).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onCompleteListener(repository.addCourseToUser(user.id!!, course))
                    course.studentsNo += 1
                    addNoOfStudents(course)
                    navigate()
                } else {
                    _status.value = STATUS.ERROR
                    _error.value = task.exception?.message
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
                repository.getAllUsers().addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val studentsList: MutableList<User> = mutableListOf()
                        for (user in snapshot.children) {
                            val userItem = user.getValue(User::class.java)
                            if (!userItem!!.teacher) {
                                repository.getStudentsOfCourse(courseId).addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        var isStudentHere = false
                                        for (student in dataSnapshot.children) {
                                            val studentItem = student.getValue(User::class.java)
                                            if (userItem.id == studentItem!!.id) {
                                                isStudentHere = true
                                                break
                                            }
                                        }
                                        if (!isStudentHere) {
                                            studentsList.add(userItem)
                                        }
                                        _students.value = studentsList
                                        _status.value = STATUS.DONE
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        _status.value = STATUS.ERROR
                                    }
                                })
                            }
                        }
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

    private fun navigate() {
        _navigate.value = true
    }

    fun finishNavigate() {
        _navigate.value = false
    }
}

@Suppress("UNCHECKED_CAST")
class AddStudentViewModelFactory (
    private val repository: FirebaseRepository,
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (AddStudentViewModel(repository) as T)
}