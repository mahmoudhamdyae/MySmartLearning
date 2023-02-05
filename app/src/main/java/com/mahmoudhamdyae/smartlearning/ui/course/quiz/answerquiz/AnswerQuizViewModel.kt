package com.mahmoudhamdyae.smartlearning.ui.course.quiz.answerquiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository

class AnswerQuizViewModel(
    private val repository: FirebaseRepository
): BaseViewModel() {
}

@Suppress("UNCHECKED_CAST")
class AnswerQuizViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (AnswerQuizViewModel(repository) as T)
}