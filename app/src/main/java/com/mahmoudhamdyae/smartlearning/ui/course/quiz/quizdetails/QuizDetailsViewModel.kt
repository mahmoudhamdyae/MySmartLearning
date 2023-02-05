package com.mahmoudhamdyae.smartlearning.ui.course.quiz.quizdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository

class QuizDetailsViewModel(
    private val repository: FirebaseRepository
): BaseViewModel() {
}

@Suppress("UNCHECKED_CAST")
class QuizDetailsViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (QuizDetailsViewModel(repository) as T)
}