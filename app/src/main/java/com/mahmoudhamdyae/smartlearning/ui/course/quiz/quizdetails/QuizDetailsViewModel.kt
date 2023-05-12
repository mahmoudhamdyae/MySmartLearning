package com.mahmoudhamdyae.smartlearning.ui.course.quiz.quizdetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizDetailsViewModel @Inject constructor(
    private val repository: FirebaseRepository
): BaseViewModel() {

    private val _students = MutableLiveData<List<User>>(mutableListOf())
    val students: LiveData<List<User>>
        get() = _students

    private val _hashMap = MutableLiveData<HashMap<User, Double>>()
    val hashMap: LiveData<HashMap<User, Double>>
        get() = _hashMap

    fun getStudents(courseId: String, quizId: String) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.getStudentsInQuiz(courseId, quizId, { hashMap ->
                _hashMap.value = hashMap
                _status.value = STATUS.DONE
            }, { students ->
                _students.value = students
                _status.value = STATUS.DONE
            }, { error ->
                if (error != null) {
                    _status.value = STATUS.ERROR
                    _error.value = error.message
                }
            })
        }
    }
}