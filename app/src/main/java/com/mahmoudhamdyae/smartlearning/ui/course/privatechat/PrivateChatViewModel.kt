package com.mahmoudhamdyae.smartlearning.ui.course.privatechat

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

class PrivateChatViewModel(
    private val repository: FirebaseRepository
) : BaseViewModel() {

    private val _students = MutableLiveData<List<User>>()
    val students: LiveData<List<User>>
        get() = _students

    fun getListOfStudents(courseId: String) {
        viewModelScope.launch {
            _status.value = STATUS.DONE
            repository.getStudentsOfCourse(courseId).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val studentsList: MutableList<User> = mutableListOf()
                    for (student in snapshot.children) {
                        val studentItem = student.getValue(User::class.java)
                        studentsList.add(studentItem!!)
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

    fun getTeacher() {
    }

    fun removeCurrentUser() {
//        _students.value.(repository.getUserData().value)
    }
}

@Suppress("UNCHECKED_CAST")
class PrivateChatViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (PrivateChatViewModel(repository) as T)
}