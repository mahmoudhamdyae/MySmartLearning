package com.mahmoudhamdyae.smartlearning.ui.course.quiz

import androidx.lifecycle.*
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

    private val _hashMap = MutableLiveData<HashMap<String, Int?>>()
    val hashMap: LiveData<HashMap<String, Int?>>
        get() = _hashMap

    fun getListOfQuizzes(courseId: String) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.getQuizzes(courseId, { hashMap ->
                _hashMap.value = hashMap
            },{ quizzes ->
                _quizzes.value = quizzes
                _status.value = STATUS.DONE
            }, { error ->
                if (error != null) {
                    _status.value = STATUS.ERROR
                    _error.value = error.message
                }
            })
        }
    }

    fun delQuiz(courseId: String, quizId: String) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.delQuiz(courseId, quizId) { error ->
                if (error == null) {
                    _status.value = STATUS.DONE
                } else {
                    _status.value = STATUS.ERROR
                    _error.value = error.message.toString()
                }
            }
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