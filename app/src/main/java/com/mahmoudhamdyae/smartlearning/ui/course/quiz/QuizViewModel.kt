package com.mahmoudhamdyae.smartlearning.ui.course.quiz

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Quiz
import kotlinx.coroutines.launch

class QuizViewModel : BaseViewModel() {

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