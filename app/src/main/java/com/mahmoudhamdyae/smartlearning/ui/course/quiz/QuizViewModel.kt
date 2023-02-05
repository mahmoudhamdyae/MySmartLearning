package com.mahmoudhamdyae.smartlearning.ui.course.quiz

import androidx.lifecycle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Quiz
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: FirebaseRepository
) : BaseViewModel() {

    private val _quizzes = MutableLiveData<List<Quiz>>()
    val quizzes: LiveData<List<Quiz>>
        get() = _quizzes

    fun getListOfQuizzes(courseId: String) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.getQuizzes(courseId).addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val quizzesList: MutableList<Quiz> = mutableListOf()
                    for (quiz in snapshot.children) {
                        val quizItem = quiz.getValue(Quiz::class.java)
                        quizzesList.add(quizItem!!)
                    }
                    _quizzes.value = quizzesList
                    _status.value = STATUS.DONE
                }

                override fun onCancelled(error: DatabaseError) {
                    _status.value = STATUS.ERROR
                    _error.value = error.message
                }
            })
        }
    }

    fun delQuiz(courseId: String, quizId: String) {
        viewModelScope.launch {
            onCompleteListener(repository.delQuiz(courseId, quizId))
        }
    }
}

@Suppress("UNCHECKED_CAST")
class QuizViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (QuizViewModel(repository) as T)
}