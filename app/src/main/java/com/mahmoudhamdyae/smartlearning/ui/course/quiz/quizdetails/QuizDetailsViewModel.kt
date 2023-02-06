package com.mahmoudhamdyae.smartlearning.ui.course.quiz.quizdetails

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

class QuizDetailsViewModel(
    private val repository: FirebaseRepository
): BaseViewModel() {

    private val _students = MutableLiveData<List<User>>()
    val students: LiveData<List<User>>
        get() = _students

    fun getStudents(courseId: String, quizId: String) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
//            repository.getStudentsInQuiz(courseId, quizId).addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    val studentsList: MutableList<String> = mutableListOf()
//                    for (material in dataSnapshot.children) {
//                        val studentItem = material.getValue(String::class.java)
//                        studentsList.add(studentItem!!)
//                    }
//                    _students.value = studentsList
//                    _status.value = STATUS.DONE
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    _status.value = STATUS.ERROR
//                }
//            })
        }
    }
}

@Suppress("UNCHECKED_CAST")
class QuizDetailsViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (QuizDetailsViewModel(repository) as T)
}