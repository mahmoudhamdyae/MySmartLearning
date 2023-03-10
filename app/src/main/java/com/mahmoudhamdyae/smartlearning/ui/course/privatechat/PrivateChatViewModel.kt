package com.mahmoudhamdyae.smartlearning.ui.course.privatechat

import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.IsTeacher
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import kotlinx.coroutines.launch

class PrivateChatViewModel(
    private val repository: FirebaseRepository
) : BaseViewModel() {

    private val _students = MutableLiveData<List<User>>()
    val students: LiveData<List<User>>
        get() = _students

    private val _teacher = MutableLiveData<User>()
    val teacher: LiveData<User>
        get() = _teacher

    fun getListOfStudents(courseId: String) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.getStudentsOfCourse(courseId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val studentsList: MutableList<User> = mutableListOf()
                    if (_isTeacher.value != IsTeacher.TEACHER) {
                        studentsList.add(_teacher.value!!)
                    }
                    for (student in snapshot.children) {
                        val studentItem = student.getValue(User::class.java)
                        if (repository.getUid() != studentItem?.id) {
                            studentsList.add(studentItem!!)
                        }
                    }
                    _students.value = studentsList
                    _status.value = STATUS.DONE
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("getStudents:Cancelled", "loadStudents:onCancelled", error.toException())
                    _status.value = STATUS.ERROR
                }
            })
        }
    }

    fun getTeacher(teacher: User) {
        _teacher.value = teacher
    }
}

@Suppress("UNCHECKED_CAST")
class PrivateChatViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (PrivateChatViewModel(repository) as T)
}