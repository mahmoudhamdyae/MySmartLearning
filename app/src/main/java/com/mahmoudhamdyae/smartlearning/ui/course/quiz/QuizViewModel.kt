package com.mahmoudhamdyae.smartlearning.ui.course.quiz

import androidx.lifecycle.*
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Quiz
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import kotlinx.coroutines.launch

class QuizViewModel(
    private val repository: FirebaseRepository
) : BaseViewModel() {

    private val _quizzes = MutableLiveData<List<Quiz>>()
    val quizzes: LiveData<List<Quiz>>
        get() = _quizzes

    init {
        getListOfQuizzes()
    }

    private fun getListOfQuizzes() {
        viewModelScope.launch {
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